package uk.ac.ebi.phenotype.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SexEstimate {
	//"Sex estimate": {
	//          "Value": -2.09818570317918,
	//          "Confidence": {
	//            "SexMale lower": -3.45029866424449,
	//            "SexMale upper": -0.746072742113871
	//          },
	//          "Level": 0.95
	//        },
	@JsonProperty("Value")
	private Double value;

	public SexEstimateConfidence getConfidence() {
		return confidence;
	}

	public void setConfidence(SexEstimateConfidence confidence) {
		this.confidence = confidence;
	}

	@JsonProperty("Confidence")
	private SexEstimateConfidence confidence;
	@JsonProperty("Level")
	private Double level;

	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getLevel() {
		return level;
	}
	public void setLevel(Double level) {
		this.level = level;
	}


}
