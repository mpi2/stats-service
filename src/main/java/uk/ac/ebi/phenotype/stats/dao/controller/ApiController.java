package uk.ac.ebi.phenotype.stats.dao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.ebi.phenotype.stats.dao.StatisticsRepository;
import uk.ac.ebi.phenotype.stats.model.Statistics;


@RequestMapping(value = "/api")
@RestController
public class ApiController {
	
	@Autowired
	StatisticsRepository statisticsRepository;
	
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("/singleStatistic")
	@ResponseBody
	public ResponseEntity<Statistics> getUniqueStatisticResult() {
		System.out.println("hitting singleStatisics endpoint");
		
		List<Statistics> singelStatistic=null;
		
		singelStatistic=statisticsRepository.findByGeneAccession("MGI:2443170");
	System.out.println("stats size="+singelStatistic.size());
		return new ResponseEntity<Statistics>(singelStatistic.get(0), HttpStatus.OK);
	}
	
	

}
