package uk.ac.ebi.phenotype.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenotypeEstimateConfidence {
//  "confidence": {
//  "lower": 0,
//  "upper": 0.00815121456852027
//},
	@JsonProperty("Genotypeexperimental lower")
	private Double lower;

	@JsonProperty("Genotypeexperimental upper")
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
