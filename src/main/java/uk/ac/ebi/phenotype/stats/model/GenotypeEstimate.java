package uk.ac.ebi.phenotype.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenotypeEstimate {
//	"genotype_estimate": {
//    "value": 0,
//    "confidence": {
//      "lower": 0,
//      "upper": 0.00815121456852027
//    },
//    "level": 0.95
//  },

	@JsonProperty("Value")
	private Double value;
	@JsonProperty("Confidence")
	private Confidence confidence;
	@JsonProperty("Level")
	private Double level;

	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Confidence getConfidence() {
		return confidence;
	}
	public void setConfidence(Confidence confidence) {
		this.confidence = confidence;
	}
}
