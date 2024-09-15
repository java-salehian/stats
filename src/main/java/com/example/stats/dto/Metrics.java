package com.example.stats.dto;

import com.example.stats.entity.MetricsEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.util.concurrent.AtomicDouble;

import java.util.concurrent.atomic.AtomicInteger;

public class Metrics {

    @JsonProperty("app_id")
    private int appId;

    @JsonProperty("country_code")
    private String countryCode;

    private AtomicInteger impressions;

    private AtomicInteger clicks;

    private AtomicDouble revenue;

    public Metrics() {
    }

    public Metrics(int appId, String countryCode, AtomicInteger impressions, AtomicInteger clicks, AtomicDouble revenue) {
        this.appId = appId;
        this.countryCode = countryCode;
        this.impressions = impressions;
        this.clicks = clicks;
        this.revenue = revenue;
    }

    public Metrics(MetricsEntity metricsEntity) {
        this.appId = metricsEntity.getAppId();
        this.countryCode = metricsEntity.getCountryCode();
        this.impressions = new AtomicInteger(metricsEntity.getImpressions());
        this.clicks = new AtomicInteger(metricsEntity.getClicks());
        this.revenue = new AtomicDouble(metricsEntity.getRevenue());
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

    public AtomicInteger getImpressions() {
        return impressions;
    }

    public void setImpressions(AtomicInteger impressions) {
        this.impressions = impressions;
    }

    public AtomicInteger getClicks() {
        return clicks;
    }

    public void setClicks(AtomicInteger clicks) {
        this.clicks = clicks;
    }

    public AtomicDouble getRevenue() {
        return revenue;
    }

    public void setRevenue(AtomicDouble revenue) {
        this.revenue = revenue;
    }

    public void incrementClicksAndRevenue(double revenue) {
        this.clicks.incrementAndGet();
        this.revenue.addAndGet(revenue);
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "appId=" + appId +
                ", countryCode='" + countryCode + '\'' +
                ", impressions=" + impressions +
                ", clicks=" + clicks +
                ", revenue=" + revenue +
                '}';
    }
}
