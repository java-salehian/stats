package com.example.stats.controller;

import com.example.stats.dto.Metrics;
import com.example.stats.service.MetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricsController {
    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping
    public ResponseEntity<List<Metrics>> getMetrics() {
        return ResponseEntity.ok(metricsService.getMetrics());
    }

    @GetMapping("/local")
    public ResponseEntity<Collection<Metrics>> getLocalMetrics() {
        return ResponseEntity.ok(metricsService.getLocalMetrics());
    }

    @GetMapping("/flush")
    public ResponseEntity flushMetricsMap() {
        metricsService.flushMetricsMap();
        return ResponseEntity.ok().build();
    }
}
