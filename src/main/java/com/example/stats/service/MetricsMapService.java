package com.example.stats.service;

import com.example.stats.dto.Metrics;
import com.example.stats.entity.MetricsEntity;
import com.example.stats.enums.IncrementType;
import com.example.stats.repository.MetricsRepository;
import com.google.common.util.concurrent.AtomicDouble;
import kotlin.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MetricsMapService {

    private final MetricsRepository metricsRepository;
    Logger logger = LoggerFactory.getLogger(MetricsMapService.class);
    private ConcurrentMap<Pair<Integer, String>, Metrics> map = new ConcurrentHashMap<>(100, 0.75F, 16);

    public MetricsMapService(MetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    public void increment(int appId, String countryCode, IncrementType field, Double value) {
        Pair<Integer, String> key = new Pair<>(appId, countryCode);
        switch (field) {
            case IMPRESSION -> {
                if (map.containsKey(key)) map.get(key).getImpressions().incrementAndGet();
                else
                    map.put(key, new Metrics(appId, countryCode, new AtomicInteger(1), new AtomicInteger(0), new AtomicDouble(0)));
            }
            case CLICK -> {
                if (map.containsKey(key)) {
                    map.get(key).incrementClicksAndRevenue(value);
                } else {
                    map.put(key, new Metrics(appId, countryCode, new AtomicInteger(0), new AtomicInteger(1), new AtomicDouble(value)));
                }
            }
        }
    }

    public void flush() {
        if (map.size() > 0) {
            logger.info("flush metrics map of size: " + map.size());
            ConcurrentMap<Pair<Integer, String>, Metrics> temp = map;
            map = new ConcurrentHashMap<>(100, 0.75F, 16);

            List<MetricsEntity> toUpdate = new ArrayList<>();

            temp.values().forEach(metrics -> {
                MetricsEntity metricsEntity = metricsRepository.findByAppIdAndCountryCode(metrics.getAppId(), metrics.getCountryCode());
                if (metricsEntity == null) {
                    metricsEntity = new MetricsEntity(metrics);
                } else {
                    metricsEntity.setImpressions(metricsEntity.getImpressions() + metrics.getImpressions().get());
                    metricsEntity.setClicks(metricsEntity.getClicks() + metrics.getClicks().get());
                    metricsEntity.setRevenue(metricsEntity.getRevenue() + metrics.getRevenue().get());
                }
                toUpdate.add(metricsEntity);
            });

            metricsRepository.saveAll(toUpdate);
            logger.info("number of updated metrics: " + toUpdate.size());
        }
    }

    public Collection<Metrics> getMapValues() {
        return map.values();
    }
}