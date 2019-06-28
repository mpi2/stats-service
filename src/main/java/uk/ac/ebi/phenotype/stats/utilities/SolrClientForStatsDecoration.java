package uk.ac.ebi.phenotype.stats.utilities;

import java.io.IOException;
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

@Service
public class SolrClientForStatsDecoration {
	
	public String solrServerRoot="http://ves-ebi-d2.ebi.ac.uk:8990/solr/pipeline";
	
	HttpSolrClient.Builder builder;
	HttpSolrClient solrClient;
	private Map<String,String> paramaterStableIdToStableKey;
	
	public SolrClientForStatsDecoration() {
		super();
		this.builder=new Builder(solrServerRoot);
		this.solrClient = builder.build();
	}

	public void populateImpressPipelineStableKeys() {
		//http://ves-ebi-d0.ebi.ac.uk:8986/solr/pipeline/select?&facet.limit=-1&facet=on&q=*:*&facet.limit=3%20&facet.pivot.mincount=1&facet.pivot=parameter_stable_id,parameter_stable_key&rows=0
		paramaterStableIdToStableKey=new HashMap<>();
		
		String pivotFacet = "parameter_stable_id,parameter_stable_key";
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setFacet(true);
		query.setFacetLimit(-1);
		query.add("facet.pivot", pivotFacet );
		System.out.println("solr query="+query);
		NamedList<List<PivotField>> facetPivot = null;
		try {
			facetPivot = solrClient.query(query).getFacetPivot();
			for( PivotField pivot : facetPivot.get(pivotFacet)){
				System.out.println(pivot.getField()+" facet value="+pivot.getValue());
				List<PivotField> pivotParamNKey = pivot.getPivot();
				System.out.println(pivotParamNKey.get(0).getField()+pivotParamNKey.get(0).getValue());
				paramaterStableIdToStableKey.put(pivot.getValue().toString(), pivotParamNKey.get(0).getValue().toString());
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
}
