package com.example.stats.service;

import com.example.stats.dto.Click;
import com.example.stats.dto.Impression;
import com.example.stats.entity.ImpressionEntity;
import com.example.stats.enums.IncrementType;
import com.example.stats.repository.ImpressionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProcessorService {

    private final ImpressionRepository impressionRepository;
    private final MetricsMapService metricsMapService;
    private final RecommenderMapService recommenderMapService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(ProcessorService.class);

    public ProcessorService(ImpressionRepository impressionRepository, MetricsMapService metricsMapService, RecommenderMapService recommenderMapService) {
        this.impressionRepository = impressionRepository;
        this.metricsMapService = metricsMapService;
        this.recommenderMapService = recommenderMapService;
    }

    @Async
    public CompletableFuture<Integer> readImpressions(String filePath) {
        logger.info("processing impression file: " + filePath);
        int count = 0;
        List<ImpressionEntity> toUpdate = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                logger.info("processing impression line: " + line);
                try {
                    Impression impression = objectMapper.readValue(line.trim(), Impression.class);
                    ImpressionEntity impressionEntity = new ImpressionEntity(impression);
                    toUpdate.add(impressionEntity);
                    metricsMapService.increment(impression.getAppId(), impression.getCountryCode(), IncrementType.IMPRESSION, null);
                    recommenderMapService.increment(impression.getAppId(), impression.getCountryCode(), impression.getAdvertiserId(), IncrementType.IMPRESSION, null);
                    count++;
                } catch (JsonProcessingException e) {
                    logger.error("error in processing impression line: " + line);
                    e.printStackTrace();
                }
                logger.info("end of processing impression line: " + line);
            }
            impressionRepository.saveAll(toUpdate);
        } catch (IOException e) {
            logger.error("error in processing impression file: " + filePath);
            e.printStackTrace();
        }
        logger.info("end of processing impression file: " + filePath);
        return CompletableFuture.completedFuture(count);
    }

    @Async
    public CompletableFuture<Integer> readClicks(String filePath) {
        logger.info("processing click file: " + filePath);
        int count = 0;
        List<ImpressionEntity> toUpdate = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                logger.info("processing click line: " + line);
                try {
                    Click click = objectMapper.readValue(line.trim(), Click.class);
                    ImpressionEntity impressionEntity = impressionRepository.findByIdIs(click.getImpressionId());
                    if (impressionEntity != null) {
                        impressionEntity.setRevenue(click.getRevenue());
                        toUpdate.add(impressionEntity);
                        metricsMapService.increment(impressionEntity.getAppId(), impressionEntity.getCountryCode(), IncrementType.CLICK, click.getRevenue());
                        recommenderMapService.increment(impressionEntity.getAppId(), impressionEntity.getCountryCode(), impressionEntity.getAdvertiserId(), IncrementType.CLICK, click.getRevenue());
                        count++;
                    } else {
                        logger.error("no impression found dor click: " + line);
                    }
                } catch (JsonProcessingException e) {
                    logger.error("error in processing click line: " + line);
                    e.printStackTrace();
                }
                logger.info("end of processing click line: " + line);
            }
            impressionRepository.saveAll(toUpdate);
        } catch (IOException e) {
            logger.error("error in processing click file: " + filePath);
            e.printStackTrace();
        }
        logger.info("end of processing click file: " + filePath);
        return CompletableFuture.completedFuture(count);
    }

}
