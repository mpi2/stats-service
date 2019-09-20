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
	private GenotypeEstimateConfidence confidence;

	public Double getLevel() {
		return level;
	}

	public void setLevel(Double level) {
		this.level = level;
	}

	@JsonProperty("Level")
	private Double level;

	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public GenotypeEstimateConfidence getConfidence() {
		return confidence;
	}
	public void setConfidence(GenotypeEstimateConfidence confidence) {
		this.confidence = confidence;
	}


}
