package com.example.stats.service;

import com.example.stats.dto.Recommendation;
import com.example.stats.entity.RecommenderEntity;
import com.example.stats.repository.RecommenderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final RecommenderMapService recommenderMapService;

    private final RecommenderRepository recommenderRepository;

    public RecommendationService(RecommenderMapService recommenderMapService, RecommenderRepository recommenderRepository) {
        this.recommenderMapService = recommenderMapService;
        this.recommenderRepository = recommenderRepository;
    }

    public List<Recommendation> getTop5Advertisers() {
        List<String[]> distinctAppIdAndCountryCodes = recommenderRepository.findDistinctAppIdAndCountryCodes();
        List<Recommendation> result = new ArrayList<>();

        distinctAppIdAndCountryCodes.forEach(pair -> {
            List<RecommenderEntity> all = recommenderRepository.findTop5RevenuePerImpressionsByAppIdAndCountryCode(Integer.parseInt(pair[0]), pair[1]);
            List<Integer> top5 = all.stream().map(RecommenderEntity::getAdvertiserId).collect(Collectors.toList());
            Recommendation recommendation = new Recommendation(Integer.parseInt(pair[0]), pair[1], top5);
            result.add(recommendation);
        });

        return result;
    }

    public List<Recommendation> getLocalTop5Advertisers() {
        return recommenderMapService.getTop5s();
    }

    public void flushRecommenderMap() {
        recommenderMapService.flush();
    }
}