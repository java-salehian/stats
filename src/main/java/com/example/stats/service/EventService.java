package com.example.stats.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EventService {

    private final ProcessorService processorService;

    public EventService(ProcessorService processorService) {
        this.processorService = processorService;
    }

    public void processEvents(List<String> impressions, List<String> clicks) {
        CompletableFuture[] counts = new CompletableFuture[impressions.size()];
        for (int i = 0; i < impressions.size(); i++) {
            String filePath = impressions.get(i);
            CompletableFuture<Integer> count = processorService.readImpressions(filePath);
            counts[i] = count;
        }
        CompletableFuture.allOf(counts).join();

        counts = new CompletableFuture[clicks.size()];
        for (int i = 0; i < clicks.size(); i++) {
            String filePath = clicks.get(i);
            CompletableFuture<Integer> count = processorService.readClicks(filePath);
            counts[i] = count;
        }
        CompletableFuture.allOf(counts).join();


    }
}
