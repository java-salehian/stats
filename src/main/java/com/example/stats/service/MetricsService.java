package com.example.stats.service;

import com.example.stats.dto.Metrics;
import com.example.stats.repository.MetricsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MetricsService {

    private final MetricsRepository metricsRepository;
    private final MetricsMapService metricsMapService;

    public MetricsService(MetricsRepository metricsRepository, MetricsMapService metricsMapService) {
        this.metricsRepository = metricsRepository;
        this.metricsMapService = metricsMapService;
    }

    public List<Metrics> getMetrics() {
        List<Metrics> result = new ArrayList<>();
        metricsRepository.findAll().forEach(metricsEntity -> {
            result.add(new Metrics(metricsEntity));
        });
        return result;
    }

    public Collection<Metrics> getLocalMetrics() {
        return metricsMapService.getMapValues();
    }

    public void flushMetricsMap() {
        metricsMapService.flush();
    }
}