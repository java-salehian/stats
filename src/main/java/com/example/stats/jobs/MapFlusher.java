package com.example.stats.jobs;

import com.example.stats.service.MetricsMapService;
import com.example.stats.service.RecommenderMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MapFlusher {

    Logger logger = LoggerFactory.getLogger(MapFlusher.class);

    private final MetricsMapService metricsMapService;
    private final RecommenderMapService recommenderMapService;

    public MapFlusher(MetricsMapService metricsMapService, RecommenderMapService recommenderMapService) {
        this.metricsMapService = metricsMapService;
        this.recommenderMapService = recommenderMapService;
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    public void processFlush() {
        logger.info("flusher job started");
        metricsMapService.flush();
        recommenderMapService.flush();
        logger.info("flusher job ended");
    }
}
