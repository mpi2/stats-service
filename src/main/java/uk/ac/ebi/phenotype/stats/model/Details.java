package uk.ac.ebi.phenotype.stats.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Details {
	

	public String getObservationType() {
		return observationType;
	}

	public void setObservationType(String observationType) {
		this.observationType = observationType;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	@JsonProperty("response_type")
	private String responseType;
	
	@JsonProperty("raw_data_summary_statistics")
	private RawSummaryStatistics rawDataSummaryStatistics;
	
   @JsonProperty("observation_type")//: "unidimensional")
	private String observationType;

	@JsonProperty("Experiment detail")
	private Map<String,String> experimentDetail;
	

	public Map<String, String> getExperimentDetail() {
		return experimentDetail;
	}

//	 "Experiment detail": {
//		"status": "Successful",
//				"procedure_group": "IMPC_CSD",
//				"procedure_stable_id": "IMPC_CSD_003",
//				"procedure_name": "Combined SHIRPA and Dysmorphology",
//				"parameter_stable_id": "IMPC_CSD_032_001",
//				"parameter_name": "Locomotor activity",
//				"phenotyping_center": "BCM",
//				"allele_symbol": "Tango2<em1(IMPC)Bay>",
//				"allele_accession_id": "MGI:6257625",
//				"gene_symbol": "Tango2",
//				"gene_accession_id": "MGI:101825",
//				"pipeline_name": "BCM Pipeline",
//				"pipeline_stable_id": "BCM_001",
//				"strain_accession_id": "MGI:2159965",
//				"metadata_group": "90a6d0764193bc4243363bcdcc04be6e",
//				"zygosity": "homozygote",
//				"colony_id": "TANGB"
//	},

	public void setExperimentDetail(Map<String,String> experimentDetail) {
		this.experimentDetail = experimentDetail;
	}

	@JsonProperty("Original_sex")
	private List<String> originalSex;
	
	@JsonProperty("Original_biological_sample_group")
	private List<String> originalBiologicalSampleGroup;

	private List<String> originalResponse;//values
    @JsonProperty("Original_date_of_experiment")
	private List<String> originalDateOfExperiment;
    @JsonProperty("Original_body_weight")
	private List<Float> originalBodyWeight;
	
	@JsonProperty("points")
	private List<Point> points=new ArrayList<>();
	
	
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public RawSummaryStatistics getRawDataSummaryStatistics() {
		return rawDataSummaryStatistics;
	}
	public void setRawDataSummaryStatistics(RawSummaryStatistics rawDataSummaryStatistics) {
		this.rawDataSummaryStatistics = rawDataSummaryStatistics;
	}
	//dont print the sex on its own in the rest service as we want only the object representing each point.
	@JsonIgnore
	public List<String> getOriginalSex() {
		return originalSex;
	}
	
	
	public void setOriginalSex(List<String> originalSex) {
		this.originalSex = originalSex;
	}
	
	@JsonIgnore
	public List<String> getOriginalBiologicalSampleGroup() {
		return originalBiologicalSampleGroup;
	}
	
	
	public void setOriginalBiologicalSampleGroup(List<String> originalBiologicalSampleGroup) {
		this.originalBiologicalSampleGroup = originalBiologicalSampleGroup;
	}
	
	@JsonIgnore
	public List<String> getOriginalResponse() {
		return originalResponse;
	}
	
	@JsonIgnore
	public List<String> getOriginalDateOfExperiment() {
		return originalDateOfExperiment;
	}
	@JsonProperty("original_date_of_experiment")
	public void setOriginalDateOfExperiment(List<String> originalDateOfExperiment) {
		this.originalDateOfExperiment = originalDateOfExperiment;
	}
	
	@JsonIgnore
	public List<Float> getOriginalBodyWeight() {
		return originalBodyWeight;
	}
	
	@JsonProperty("original_body_weight")
	public void setOriginalBodyWeight(List<Float> originalBodyWeight) {
		this.originalBodyWeight = originalBodyWeight;
	}
	
	@JsonProperty("Original_response")
	public void setOriginalResponse(List<String> originalResponse) {
		this.originalResponse = originalResponse;
	}
	
	
	public List<Point> getPoints() {
//		List<Point> points=new ArrayList<>();
//		
//		for(int i=0; i<originalResponse.size(); i++) {
//			points.add(new Point(originalResponse.get(i),originalSex.get(i), originalBiologicalSampleGroup.get(i), originalBodyWeight.get(i) ));
//		}
		return this.points;
	}
	
	public List<Point> setPoints() {
		
		for(int i=0; i<originalResponse.size(); i++) {
			points.add(new Point(originalResponse.get(i),originalSex.get(i), originalBiologicalSampleGroup.get(i), originalBodyWeight.get(i) , originalDateOfExperiment.get(i)));
		}
		System.out.println("set point size="+this.points.size());
		return points;
	}

	@Override
	public String toString() {
		return "Details{" +
				"responseType='" + responseType + '\'' +
				", rawDataSummaryStatistics=" + rawDataSummaryStatistics +
				", observationType='" + observationType + '\'' +
				", experimentDetail=" + experimentDetail +
				", originalSex=" + originalSex +
				", originalBiologicalSampleGroup=" + originalBiologicalSampleGroup +
				", originalResponse=" + originalResponse +
				", originalDateOfExperiment=" + originalDateOfExperiment +
				", originalBodyWeight=" + originalBodyWeight +
				", points=" + points +
				'}';
	}
}
