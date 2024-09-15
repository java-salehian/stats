package com.example.stats.service;

import com.example.stats.dto.Recommendation;
import com.example.stats.dto.Recommender;
import com.example.stats.entity.RecommenderEntity;
import com.example.stats.enums.IncrementType;
import com.example.stats.repository.RecommenderRepository;
import com.google.common.util.concurrent.AtomicDouble;
import kotlin.Pair;
import kotlin.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class RecommenderMapService {

    private final RecommenderRepository recommenderRepository;
    Logger logger = LoggerFactory.getLogger(RecommenderMapService.class);
    private ConcurrentMap<Triple<Integer, String, Integer>, Recommender> map = new ConcurrentHashMap<>(100, 0.75F, 16);

    public RecommenderMapService(RecommenderRepository recommenderRepository) {
        this.recommenderRepository = recommenderRepository;
    }

    public void increment(int appId, String countryCode, int advertiserId, IncrementType field, Double value) {
        Triple<Integer, String, Integer> key = new Triple<>(appId, countryCode, advertiserId);
        switch (field) {
            case IMPRESSION -> {
                if (map.containsKey(key)) {
                    map.get(key).getImpressions().incrementAndGet();
                } else {
                    map.put(key, new Recommender(appId, countryCode, advertiserId, new AtomicInteger(1), new AtomicDouble(0)));
                }
            }
            case CLICK -> {
                if (map.containsKey(key)) {
                    map.get(key).getRevenue().addAndGet(value);
                } else {
                    map.put(key, new Recommender(appId, countryCode, advertiserId, new AtomicInteger(0), new AtomicDouble(value)));
                }
            }
        }
    }

    public void flush() {
        if (map.size() > 0) {
            logger.info("flush recommender map of size: " + map.size());
            ConcurrentMap<Triple<Integer, String, Integer>, Recommender> temp = map;
            map = new ConcurrentHashMap<>(100, 0.75F, 16);

            List<RecommenderEntity> toUpdate = new ArrayList<>();

            temp.values().forEach(recommender -> {
                RecommenderEntity recommenderEntity = recommenderRepository.findByAppIdAndCountryCodeAndAdvertiserId(recommender.getAppId(), recommender.getCountryCode(), recommender.getAdvertiserId());
                if (recommenderEntity == null) {
                    recommenderEntity = new RecommenderEntity(recommender);
                } else {
                    recommenderEntity.setImpressions(recommenderEntity.getImpressions() + recommender.getImpressions().get());
                    recommenderEntity.setRevenue(recommenderEntity.getRevenue() + recommender.getRevenue().get());
                }
                toUpdate.add(recommenderEntity);
            });

            recommenderRepository.saveAll(toUpdate);
            logger.info("number of updated recommender: " + toUpdate.size());
        }
    }

    public List<Recommendation> getTop5s() {
        Map<Pair<Integer, String>, List<Recommender>> groupedBy = map.values().stream().collect(Collectors.groupingBy(entity -> new Pair<>(entity.getAppId(), entity.getCountryCode())));

        List<Recommendation> result = new ArrayList<>();
        groupedBy.keySet().forEach(pair -> {
            List<Integer> top5 = groupedBy.get(pair).stream().sorted(Comparator.comparingDouble(Recommender::getRatio).reversed())
                    .limit(5)
                    .map(Recommender::getAdvertiserId)
                    .collect(Collectors.toList());
            Recommendation recommendation = new Recommendation(pair.getFirst(), pair.getSecond(), top5);
            result.add(recommendation);
        });

        return result;
    }
}