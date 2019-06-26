package uk.ac.ebi.phenotype.stats.dao.controller;

import static org.springframework.web.bind.annotation.ValueConstants.DEFAULT_NONE;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.ebi.phenotype.stats.dao.StatisticsRepository;
import uk.ac.ebi.phenotype.stats.model.Details;
import uk.ac.ebi.phenotype.stats.model.ExperimentDetails;
import uk.ac.ebi.phenotype.stats.model.Result;
import uk.ac.ebi.phenotype.stats.model.Statistics;



@RequestMapping(value = "/api")
@RestController
public class ApiController {
	
	@Autowired
	StatisticsRepository statisticsRepository;
	
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("/stats")
	@ResponseBody
	public ResponseEntity<List<Statistics>> getUniqueStatisticResult(
            @RequestParam(required = true, value = "accession") String[] accession,
            @RequestParam(required = false, value = "strain_accession_id") String strain,
            @RequestParam(required = false, value = "allele_accession_id") String alleleAccession,
            @RequestParam(required = false, value = "metadata_group", defaultValue = DEFAULT_NONE) String metadataGroup,
            @RequestParam(required = true, value = "parameter_stable_id") String parameterStableId,
            //@RequestParam(required = false, value = "gender") String[] gender,
            @RequestParam(required = false, value = "zygosity") String[] zygosity,
            @RequestParam(required = false, value = "phenotyping_center") String phenotypingCenter,
            @RequestParam(required = false, value = "pipeline_stable_id") String pipelineStableId,
			Model model) {
		//http://ves-ebi-d1.ebi.ac.uk:8091/api/stats?accession=MGI:104874&parameter_stable_id=ESLIM_009_001_702&pipeline_stable_id=ESLIM_002
		// http://localhost:8080/api/singleStatistic?accession=MGI:2443170&strain_accession_id=MGI:2159965&allele_accession_id=MGI:2159965&zygosity=homozygote
		System.out.println("hitting singleStatisics endpoint");

		List<Statistics> listOfStatistics = null;
		Statistics singleStatistics = new Statistics();
		Statistics filterStatistics = new Statistics();
//		Result result=new Result();
//		Details details=new Details();
//		ExperimentDetails experimentDetails=new ExperimentDetails();

		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
		int numOfParams = 0;
		HttpStatus status=HttpStatus.ACCEPTED;
		if (accession != null) {
			exampleMatcher.withMatcher(accession[0], GenericPropertyMatcher::ignoreCase);
			filterStatistics.setGeneAccession(accession[0]);
			numOfParams++;
		}

		if (alleleAccession != null) {
			exampleMatcher.withMatcher(alleleAccession, GenericPropertyMatcher::ignoreCase).withIgnoreNullValues();
			filterStatistics.setAlleleAccession(alleleAccession);
			numOfParams++;
		}

		if (metadataGroup != null) {
			exampleMatcher.withMatcher(metadataGroup, GenericPropertyMatcher::ignoreCase);
			filterStatistics.setMetaDataGroup(metadataGroup);
			numOfParams++;
		}

		if (parameterStableId != null) {
			exampleMatcher.withMatcher(parameterStableId, GenericPropertyMatcher::ignoreCase);
			filterStatistics.setParameterStableId(parameterStableId);
			numOfParams++;
		}

		if (zygosity != null && zygosity.length > 0) {
			String zyg = zygosity[0];
			exampleMatcher.withMatcher(zyg, GenericPropertyMatcher::ignoreCase);
			filterStatistics.setZygosity(zyg);
			numOfParams++;
		}

		if (phenotypingCenter != null) {
			exampleMatcher.withMatcher(phenotypingCenter, GenericPropertyMatcher::ignoreCase);
			filterStatistics.setPhenotypingCenter(phenotypingCenter);
			numOfParams++;
		}

		if (pipelineStableId != null) {
			exampleMatcher.withMatcher(pipelineStableId, GenericPropertyMatcher::ignoreCase);
			filterStatistics.setPipelineStableId(pipelineStableId);
			numOfParams++;
		}
		Example<Statistics> example = Example.of(filterStatistics, exampleMatcher);
		System.out.println("example=" + example);
		System.out.println("numOfParams="+numOfParams);
		
			
			listOfStatistics = statisticsRepository.findAll(example);
			//Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
			//Page<Statistics> results = statisticsRepository.findAll(example, firstPageWithTwoElements);
//			if(listOfStatistics.size()==1) {
//				singleStatistics=listOfStatistics.get(0);
//			}

//			if (strain != null && !(listOfStatistics==null ||listOfStatistics.isEmpty())) {// cant do nested filtering in the same way as above so old fashioned filter
//				for (Statistics temp : listOfStatistics) {
//					if (temp.getResult().getDetails().getExperimentDetails().getStrainAccessionId()
//							.equalsIgnoreCase(strain)) {
//						singleStatistics = temp;
//					}
//				}
//			} 

			System.out.println("stats size=" + listOfStatistics.size());
			 if(listOfStatistics.size()>1) {
			System.err.println("more than one result being returned form repository for singleStatistics request");
			 }
		return new ResponseEntity<List<Statistics>>(listOfStatistics, status);
	}
	

}
