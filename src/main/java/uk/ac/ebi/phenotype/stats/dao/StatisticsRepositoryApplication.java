package uk.ac.ebi.phenotype.stats.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import uk.ac.ebi.phenotype.stats.StatisticsDataLoader;

@SpringBootApplication
public class StatisticsRepositoryApplication implements CommandLineRunner{
	@Autowired
	private StatisticsRepository statisticsRepository;

	public static void main(String []args) {
		System.out.println("running main stats service method in StatisticsRepositoryApplication!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		SpringApplication.run(StatisticsRepositoryApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("running repository as a service");
		
	}

	public StatisticsRepository getStatisticsRepository() {
		return statisticsRepository;
	}

	public void setStatisticsRepository(StatisticsRepository statisticsRepository) {
		this.statisticsRepository = statisticsRepository;
	}
	
	
	
	
	

}
