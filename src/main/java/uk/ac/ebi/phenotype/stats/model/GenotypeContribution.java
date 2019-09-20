package uk.ac.ebi.phenotype.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenotypeContribution {

//"Genotype contribution": {
//		"Overal": 0.491616333596917,
//				"Sex FvKO p-value": 0.733997771962571,
//				"Sex MvKO p-value": 0.155455797237431,
//				"Sexual dimorphism detected": false
//	},
	@JsonProperty("Overal")
	private Double overal;
	public Double getOveral() {
		return overal;
	}
	public void setOveral(Double overal) {
		this.overal = overal;
	}
	public Double getSexFvkoPVal() {
		return sexFvkoPVal;
	}
	public void setSexFvkoPVal(Double sexFvkoPVal) {
		this.sexFvkoPVal = sexFvkoPVal;
	}
	public String getSexualDimorphismDetected() {
		return sexualDimorphismDetected;
	}
	public void setSexualDimorphismDetected(String sexualDimorphismDetected) {
		this.sexualDimorphismDetected = sexualDimorphismDetected;
	}
	@JsonProperty("Sex FvKO p-value")
	private Double sexFvkoPVal;

	public Double getSexMvkoPValue() {
		return sexMvkoPValue;
	}

	public void setSexMvkoPValue(Double sexMvkoPValue) {
		this.sexMvkoPValue = sexMvkoPValue;
	}

	@JsonProperty("Sex MvKO p-value")
	private Double sexMvkoPValue;
	@JsonProperty("Sexual dimorphism detected")
	private String sexualDimorphismDetected;
	
}
