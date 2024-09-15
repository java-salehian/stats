package com.example.stats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Recommendation {

    @JsonProperty("app_id")
    private int appId;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("recommended_advertiser_ids")
    private List<Integer> recommendedAdvertiserIds;

    public Recommendation() {
    }

    public Recommendation(int appId, String countryCode, List<Integer> recommendedAdvertiserIds) {
        this.appId = appId;
        this.countryCode = countryCode;
        this.recommendedAdvertiserIds = recommendedAdvertiserIds;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<Integer> getRecommendedAdvertiserIds() {
        return recommendedAdvertiserIds;
    }

    public void setRecommendedAdvertiserIds(List<Integer> recommendedAdvertiserIds) {
        this.recommendedAdvertiserIds = recommendedAdvertiserIds;
    }
}
