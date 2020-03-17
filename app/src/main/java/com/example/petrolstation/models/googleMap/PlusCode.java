package com.example.petrolstation.models.googleMap;

import java.util.HashMap;
import java.util.Map;

class PlusCode {
    private String compoundCode;
    private String globalCode;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
