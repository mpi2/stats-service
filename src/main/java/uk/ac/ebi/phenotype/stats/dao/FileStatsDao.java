/******************************************************************************
 Copyright 2015 EMBL - European Bioinformatics Institute

 Licensed under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License. You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 either express or implied. See the License for the specific
 language governing permissions and limitations under the
 License.
 */
package uk.ac.ebi.phenotype.stats.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.ebi.phenotype.stats.model.Details;
import uk.ac.ebi.phenotype.stats.model.Statistics;
import uk.ac.ebi.phenotype.stats.model.StatsJson;


@Service
public class FileStatsDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //"https://www.ebi.ac.uk/~hamedhm/windowing/DR9.2/jobs/ExtractedPValues/DR9.2_V1/";
    //private static final String indexFilename="Index_V1_DR92.txt";
    private static final String successFileName="output_Successful.tsv";
	//note need to specify these directories as env variables when running from inside this sub module!!!???
	

//    public Statistics getStatsSummary(String center, String procedure, String parameter, String colonyId, String zygosity, String metadata) {
//    	String path=this.getFilePathFromIndex(center, procedure, parameter, colonyId, zygosity, metadata);
//    	Statistics result=null;
//    	if(path.isEmpty()) {
//    		System.err.println("no file at that path "+path);
//    	}else {
//    		result= this.readSuccesFile(path);
//    	}
//    	return result;
//    }
    
	public Statistics readSuccessFile(String path) throws IOException {
		Statistics stats=null;
		// need the details section of the json object
		List<String> lines = null;
		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			// stream.forEach(System.out::println);
			lines = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert (lines.size() == 1);
		String data = lines.get(0);
		String[] sections = data.split("\"Result\"");
		//System.out.println("section 0="+sections[0]+ "section1="+ sections[1] );
		String summaryInfo = sections[0].replace("{", "");// remove useless { on the end!!
		String json = "{\"result\"" + sections[1];
		// "observation_type": "unidimensional"
		// test if unidimensional and discard if not as we want numbers not strings
		if (json.contains("unidimensional")){

			// we currently have {} to represent NA but java doesn't like it as it thinks it
			// should be an object
			// so we need to replace all {} with the string null
			//String json2 = json.replace("{}", "null");

			// System.out.println("summaryInfo="+summaryInfo);

			// System.out.println("json="+json);

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);

			StatsJson value = mapper.readValue(json, StatsJson.class);
			// System.out.println(value.getResult().getDetails().getResponseType());
			// System.out.println(value.getResult().getDetails());

			stats = new Statistics(value.getResult());
			int sampleGroupSize = 0, sexSize = 0, responseSize = 0, dateOfExperimentSize = 0;
			if (value.getResult().getDetails().getOriginalBiologicalSampleGroup() != null) {
				sampleGroupSize = value.getResult().getDetails().getOriginalBiologicalSampleGroup().size();
			}
			if (value.getResult().getDetails().getOriginalSex() != null) {
				sexSize = value.getResult().getDetails().getOriginalSex().size();
			}
			if (value.getResult().getDetails().getOriginalResponse() != null) {
				responseSize = value.getResult().getDetails().getOriginalResponse().size();
			}
			if (value.getResult().getDetails().getOriginalDateOfExperiment() != null) {
				dateOfExperimentSize = value.getResult().getDetails().getOriginalDateOfExperiment().size();
			}
			// get and set the main top level info for filtering for charts
			if (value.getResult().getDetails() != null) {
				Details details = value.getResult().getDetails();
				if (details.getExperimentDetail() != null) {
					Map<String, String> experimentalDetails = details.getExperimentDetail();
					moveToMainVariables(experimentalDetails, stats);
				}
			}

			if (sampleGroupSize != sexSize || sampleGroupSize != responseSize || sampleGroupSize != dateOfExperimentSize) {
				System.err.println("sizes of point data points don't match");
			}
			// all these lists should be the same size as refer to points.
		}
		if(json.contains("categorical") ){
			//run the categorical stats objects creation here that is different objects to unidimensional
			System.out.println("running categorical stats creation!!!!!!!!!!");
		}
	return stats;
	}

	private void moveToMainVariables(Map<String,String> experimentalDetails, Statistics stats) {
		if (experimentalDetails.get("allele_accession_id") != null) {
			stats.setAlleleAccession(experimentalDetails.get("gene_accession_id"));// set as a top level variable so
																				// we can filter easily
		}

		//procedure_stable_id
		if (experimentalDetails.get("procedure_stable_id")  != null) {
			stats.setProcedureStableId(experimentalDetails.get("procedure_stable_id"));
		}
		if (experimentalDetails.get("procedure_name")  != null) {
			stats.setProcedureName(experimentalDetails.get("procedure_name"));
		}
		if (experimentalDetails.get("parameter_stable_id") != null) {
			stats.setParameterStableId(experimentalDetails.get("parameter_stable_id"));
		}
		if (experimentalDetails.get("parameter_name") != null) {
			stats.setParameterStableName(experimentalDetails.get("parameter_name"));
		}
		if (experimentalDetails.get("phenotyping_center") != null) {
			stats.setPhenotypingCenter(experimentalDetails.get("phenotyping_center"));
		}
		if (experimentalDetails.get("allele_symbol") != null) {
			stats.setAllele(experimentalDetails.get("allele_symbol"));
		}
		if (experimentalDetails.get("gene_accession_id") != null) {
			stats.setGeneAccession(experimentalDetails.get("gene_accession_id"));
		}
		if (experimentalDetails.get("gene_symbol") != null) {
			stats.setGeneSymbol(experimentalDetails.get("gene_symbol"));
		}
		if (experimentalDetails.get("pipeline_stable_id") != null) {
			stats.setPipelineStableId(experimentalDetails.get("pipeline_stable_id"));
		}
		if (experimentalDetails.get("metadata_group") != null) {
			stats.setMetaDataGroup(experimentalDetails.get("metadata_group"));
		}
		if (experimentalDetails.get("zygoisty") != null) {
			stats.setZygosity(experimentalDetails.get("zygosity"));
		}
		if (experimentalDetails.get("colony_id") != null) {
			stats.setColonyId(experimentalDetails.get("colony_id"));
		}
		
	}
    

    
	public List<String> readIndexFile(String indexFilename, String originalStatsDir, String rootStatsDirectory) {
		List<String> succesfulOnly = null;
		try (Stream<String> stream = Files.lines(Paths.get(indexFilename))) {

			succesfulOnly = stream.filter(string -> string.endsWith(successFileName))
					.map(string -> string.replace(originalStatsDir, rootStatsDirectory)).distinct()
					.collect(Collectors.toList());// .distinct().collect(Collectors.toList());

			// succesfulOnly.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return succesfulOnly;
	}


//	public List<String> getParameterOptionsForRequest(String phenotypingCenter, String parameterStableId,
//			String metadataGroup) {
//		
//		Stream<String> filePaths=succesfulOnly.stream();
//		List<String> pathsForRequest = filePaths
//		.filter(string -> string.contains(phenotypingCenter))
//		.filter(string -> string.contains(parameterStableId)).collect(Collectors.toList());
//		return pathsForRequest;
//		
//	}



	public List<Statistics> getAllStatsFromFiles(String center, String parameter, List<String> succesfulOnly) {

		if(center==null&& parameter==null){
			center="";
			parameter="";
		}
		List<Statistics> statsList = new ArrayList<>();
		for (String path : succesfulOnly) {
			// if(path.contains("IMPC_HEM_038_001")&& path.contains("MARC")) {
			//if center and parameter aren't specified they are empty strings which are always contained in the path and so are true if not specified - odd I should prob change this.?
			if (path.contains(center) && path.contains(parameter)) {

				Statistics tempStats;
				try {
					tempStats = readSuccessFile(path);
				} catch (Exception e) {
					System.err.println("hit parsing error in one file with path"+path+" should continue without adding this result, setting tempStats to null...");
					
					e.printStackTrace();
					continue;
				}
				// watch this as it's a hack to get the points as json objects in a list rather
				// than seperate arrays and so we can store them this way in mongo
				if (tempStats != null) {
					tempStats.setPoints();
					statsList.add(tempStats);
				}
			}

		}
		return statsList;

	}

    
	
}
