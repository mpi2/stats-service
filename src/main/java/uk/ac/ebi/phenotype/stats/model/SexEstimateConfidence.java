package uk.ac.ebi.phenotype.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SexEstimateConfidence {

    @JsonProperty("SexMale lower lower")
    private Double lower;

    @JsonProperty("SexMale lower upper")
    private Double upper;

    public Double getLower() {
        return lower;
    }
    public void setLower(Double lower) {
        this.lower = lower;
    }
    public Double getUpper() {
        return upper;
    }
    public void setUpper(Double upper) {
        this.upper = upper;
    }
}
