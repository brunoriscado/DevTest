package com.example.model.google.geocoding;

import java.util.List;

public class GoogleReverseGeocodingResponse {
    private List<GoogleAddress> results;
    private String status;

    public List<GoogleAddress> getResults() {
        return results;
    }

    public void setResults(List<GoogleAddress> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
