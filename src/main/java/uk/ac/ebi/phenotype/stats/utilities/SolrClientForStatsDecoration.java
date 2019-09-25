package uk.ac.ebi.phenotype.stats.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.common.util.NamedList;
import org.springframework.stereotype.Service;
import uk.ac.ebi.phenotype.stats.StatisticsDataLoader;
import uk.ac.ebi.phenotype.stats.model.Statistics;

@Service
public class SolrClientForStatsDecoration {
	
	public String solrServerRoot="http://ves-ebi-d2.ebi.ac.uk:8990/solr/pipeline";
	
	HttpSolrClient.Builder builder;
	HttpSolrClient solrClient;
	private Map<String,String> procedureStableIdToStableKey;
	private Map<String,String> paramaterStableIdToStableKey;
	
	public SolrClientForStatsDecoration() {
		super();
		this.builder=new Builder(solrServerRoot);
		this.solrClient = builder.build();
		this.populateImpressProcedureStableKeys();
		this.populateImpressParameterStableKeys();
	}

	private void populateImpressProcedureStableKeys() {
		// http://ves-ebi-d2.ebi.ac.uk:8990/solr/pipeline/select?&facet.limit=-1&facet=on&q=*:*&facet.limit=3%20&facet.pivot.mincount=1&facet.pivot=pipeline_stable_id,pipeline_stable_key&rows=0
		procedureStableIdToStableKey = new HashMap<>();

		String pivotFacet = "procedure_stable_id,procedure_stable_key";
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setFacet(true);
		query.setFacetLimit(-1);
		query.add("facet.pivot", pivotFacet);
		System.out.println("solr query=" + query);
		NamedList<List<PivotField>> facetPivot = null;
		try {
			facetPivot = solrClient.query(query).getFacetPivot();
			for (PivotField pivot : facetPivot.get(pivotFacet)) {
				//System.out.println(pivot.getField() + " facet value=" + pivot.getValue());
				List<PivotField> pivotParamNKey = pivot.getPivot();
				//System.out.println(pivotParamNKey.get(0).getField() + pivotParamNKey.get(0).getValue());
				String keyShouldBeInt=pivotParamNKey.get(0).getValue().toString();
				procedureStableIdToStableKey.put(pivot.getValue().toString(),
						pivotParamNKey.get(0).getValue().toString());
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void populateImpressParameterStableKeys() {
		//http://ves-ebi-d2.ebi.ac.uk:8990/solr/pipeline/select?&facet.limit=-1&facet=on&q=*:*&facet.limit=3%20&facet.pivot.mincount=1&facet.pivot=parameter_stable_id,parameter_stable_key&rows=0
		System.out.println("popluating Impress ids to DCC internal keys so we can make hyperlinks");
		paramaterStableIdToStableKey = new HashMap<>();

		String pivotFacet = "parameter_stable_id,parameter_stable_key";
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setFacet(true);
		query.setFacetLimit(-1);
		query.add("facet.pivot", pivotFacet);
		System.out.println("solr query=" + query);
		NamedList<List<PivotField>> facetPivot = null;
		try {
			facetPivot = solrClient.query(query).getFacetPivot();
			for (PivotField pivot : facetPivot.get(pivotFacet)) {
				System.out.println(pivot.getField() + " facet value=" + pivot.getValue());
				List<PivotField> pivotParamNKey = pivot.getPivot();
				System.out.println(pivotParamNKey.get(0).getField() + pivotParamNKey.get(0).getValue());
				paramaterStableIdToStableKey.put(pivot.getValue().toString(),
						pivotParamNKey.get(0).getValue().toString());
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String getPipelineKey(String pipelineStableId) {
		return procedureStableIdToStableKey.get(pipelineStableId);
	}

	public String getParameterKey(String parameterStableId) {
		return paramaterStableIdToStableKey.get(parameterStableId);
	}

	public List<Statistics> decorateStatsWithImpressKeys(List<Statistics> stats) {
		System.out.println("decorating stats");
		List<Statistics> decoratedStats=new ArrayList<>();
		for(Statistics stat:stats) {
			String procedureKey= getPipelineKey(stat.getProcedureStableId());
			String parameterKey= getParameterKey(stat.getParameterStableId());
			if(procedureKey==null){
				System.out.println("this pipelineStablieId internal key is null "+stat.getPipelineStableId());
			}
			else{
				stat.setImpressProcedureKey(Integer.parseInt(procedureKey));
			}
			if(parameterKey==null) {
				System.out.println("this parameterStableId internal key is null"+stat.getParameterStableId());
			}else{
				stat.setImpressParameterKey(Integer.parseInt(parameterKey));
			}
		}

		return decoratedStats;
	}
}
