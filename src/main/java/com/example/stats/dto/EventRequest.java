package com.example.stats.dto;

import java.util.List;

public class EventRequest {

    private List<String> impressions;

    private List<String> clicks;

    public EventRequest() {
    }

    public EventRequest(List<String> impressions, List<String> clicks) {
        this.impressions = impressions;
        this.clicks = clicks;
    }

    public List<String> getImpressions() {
        return impressions;
    }

    public void setImpressions(List<String> impressions) {
        this.impressions = impressions;
    }

    public List<String> getClicks() {
        return clicks;
    }

    public void setClicks(List<String> clicks) {
        this.clicks = clicks;
    }
}
