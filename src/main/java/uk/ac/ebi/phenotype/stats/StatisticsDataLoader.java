package uk.ac.ebi.phenotype.stats;

import java.util.List;

import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import uk.ac.ebi.phenotype.stats.dao.FileStatsDao;
import uk.ac.ebi.phenotype.stats.dao.Statistics;
import uk.ac.ebi.phenotype.stats.dao.StatisticsRepository;

@ComponentScan("uk.ac.ebi.phenotype.stats.dao")
@SpringBootApplication
public class StatisticsDataLoader implements CommandLineRunner {
	
	
//	private String center;
//	
//	private String parameter;
	
	@Autowired
	private StatisticsRepository statsRepository;
	private FileStatsDao statsProvider;

	
	public static void main(String []args) {
		SpringApplication.run(StatisticsDataLoader.class, args);
	}
	
	@Inject
    public StatisticsDataLoader(FileStatsDao statsProvider, StatisticsRepository statsRepository) {
		this.statsProvider=statsProvider;
		this.statsRepository=statsRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		boolean deleteFirst=false;
		String center="MARC";
		String parameter="IMPC_HEM_038_001";
		////if(path.contains("IMPC_HEM_038_001")&& path.contains("MARC")) {
		if(deleteFirst) {
		System.out.println("deleting all data from mongodb!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		statsRepository.deleteAll();
		}
		loadDataIntoMongo(center, parameter);
		System.exit(0);
	}

	private void loadDataIntoMongo(String center, String parameter) {
		List<Statistics>stats=statsProvider.getAllStatsFromFiles(center, parameter);
		System.out.println("stats size="+stats.size());
		this.saveDataToMongo(stats);
	}
	
	private void saveDataToMongo(List<Statistics>stats) {
		System.out.println("saving data to mongodb!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		statsRepository.saveAll(stats);//save in old spring saveall in new spring
	}

}
