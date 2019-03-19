package org.mousephenotype.cda.file.stats;

import org.springframework.data.annotation.Id;

public class Stats {
	
	@Id
	private String id;
	
	private String parameterStableId;
	
	private String parameterStableName;
	
	public String getParameterStableName() {
		return parameterStableName;
	}

	public void setParameterStableName(String parameterStableName) {
		this.parameterStableName = parameterStableName;
	}

	public String getParameterStableId() {
		return parameterStableId;
	}

	public void setParameterStableId(String parameterStableId) {
		this.parameterStableId = parameterStableId;
	}

	public Stats(Result result) {
		this.result=result;
	}
	
	Result result;
	
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	private String headerInfo;
	public String getHeaderInfo() {
		return headerInfo;
	}

	public void setHeaderInfo(String headerInfo) {
		this.headerInfo = headerInfo;
	}

	//set the points objects before loading into mongo
	public void setPoints() {
		result.getDetails().setPoints();
		
	}

	

}
