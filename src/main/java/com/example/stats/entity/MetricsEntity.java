package com.example.stats.entity;

import com.example.stats.dto.Metrics;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "metrics")
public class MetricsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private int appId;

    private String countryCode;

    private int impressions;

    private int clicks;

    private double revenue;

    public MetricsEntity() {
    }

    public MetricsEntity(Metrics metrics) {
        this.appId = metrics.getAppId();
        this.countryCode = metrics.getCountryCode();
        this.impressions = metrics.getImpressions().get();
        this.clicks = metrics.getClicks().get();
        this.revenue = metrics.getRevenue().get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
