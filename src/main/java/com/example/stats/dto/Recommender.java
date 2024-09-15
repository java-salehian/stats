package com.example.stats.dto;

import com.example.stats.entity.RecommenderEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.util.concurrent.AtomicDouble;

import java.util.concurrent.atomic.AtomicInteger;

public class Recommender {

    private int appId;

    private String countryCode;

    private int advertiserId;

    private AtomicInteger impressions;

    private AtomicDouble revenue;

    public Recommender() {
    }

    public Recommender(int appId, String countryCode, int advertiserId, AtomicInteger impressions, AtomicDouble revenue) {
        this.appId = appId;
        this.countryCode = countryCode;
        this.advertiserId = advertiserId;
        this.impressions = impressions;
        this.revenue = revenue;
    }

    public Recommender(RecommenderEntity recommenderEntity) {
        this.appId = recommenderEntity.getAppId();
        this.countryCode = recommenderEntity.getCountryCode();
        this.advertiserId = recommenderEntity.getAdvertiserId();
        this.impressions = new AtomicInteger(recommenderEntity.getImpressions());
        this.revenue = new AtomicDouble(recommenderEntity.getRevenue());
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

    public int getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(int advertiserId) {
        this.advertiserId = advertiserId;
    }

    public AtomicInteger getImpressions() {
        return impressions;
    }

    public void setImpressions(AtomicInteger impressions) {
        this.impressions = impressions;
    }

    public AtomicDouble getRevenue() {
        return revenue;
    }

    public void setRevenue(AtomicDouble revenue) {
        this.revenue = revenue;
    }

    public double getRatio() {
        return revenue.get() / impressions.get();
    }

    @Override
    public String toString() {
        return "Recommender{" +
                "appId=" + appId +
                ", countryCode='" + countryCode + '\'' +
                ", advertiserId=" + advertiserId +
                ", impressions=" + impressions +
                ", revenue=" + revenue +
                '}';
    }
}
