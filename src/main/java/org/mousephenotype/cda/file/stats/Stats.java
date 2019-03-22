package org.mousephenotype.cda.file.stats;

import javax.annotation.Resource;

import org.springframework.data.annotation.Id;

@Resource
public class Stats  {
	
	@Id
	private String id;
	
	private String parameterStableId;
	
	private String parameterStableName;
	
	private String allele;
	
	private String geneSymbol;
	
	private String center;
	
	private String geneAccession;
	
	private String alleleAccession;
	
	private String metaData;
	
	private String zygosity;
	
	private String colonyId;
	
	private int impressParameterKey;
	
	private int impressProtocolKey;
	
	public String getColonyId() {
		return colonyId;
	}

	public void setColonyId(String colonyId) {
		this.colonyId = colonyId;
	}

	public int getImpressParameterKey() {
		return impressParameterKey;
	}

	public void setImpressParameterKey(int impressParameterKey) {
		this.impressParameterKey = impressParameterKey;
	}

	public int getImpressProtocolKey() {
		return impressProtocolKey;
	}

	public void setImpressProtocolKey(int impressProtocolKey) {
		this.impressProtocolKey = impressProtocolKey;
	}

	public String getZygosity() {
		return zygosity;
	}

	public void setZygosity(String zygosity) {
		this.zygosity = zygosity;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public String getGeneAccession() {
		return geneAccession;
	}

	public void setGeneAccession(String geneAccession) {
		this.geneAccession = geneAccession;
	}

	public String getAlleleAccession() {
		return alleleAccession;
	}

	public void setAlleleAccession(String alleleAccession) {
		this.alleleAccession = alleleAccession;
	}

	public String getGeneSymbol() {
		return geneSymbol;
	}

	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getAllele() {
		return allele;
	}

	public void setAllele(String allele) {
		this.allele = allele;
	}

	public Stats() {
		super();
	}

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

	@Override
	public String toString() {
		return "Stats [id=" + id + ", parameterStableId=" + parameterStableId + ", parameterStableName="
				+ parameterStableName + ", result=" + result + ", headerInfo=" + headerInfo + "]";
	}

	

}
