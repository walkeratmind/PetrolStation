package com.example.petrolstation.models.googleMap;

import java.util.HashMap;
import java.util.Map;

public class OpeningHours {
    private Boolean openNow;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
