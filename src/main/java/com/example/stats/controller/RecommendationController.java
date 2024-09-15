package com.example.stats.controller;

import com.example.stats.dto.Recommendation;
import com.example.stats.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping()
    public List<Recommendation> getRecommendations() {
        return recommendationService.getTop5Advertisers();
    }

    @GetMapping("/local")
    public ResponseEntity<List<Recommendation>> getLocalRecommender() {
        return ResponseEntity.ok(recommendationService.getLocalTop5Advertisers());
    }

    @GetMapping("/flush")
    public ResponseEntity flushRecommenderMap() {
        recommendationService.flushRecommenderMap();
        return ResponseEntity.ok().build();
    }
}
