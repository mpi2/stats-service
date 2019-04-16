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
	
	@Autowired
	private StatisticsRepository statsRepository;
	private FileStatsDao statsProvider;

	
	public static void main(String []args) {
		System.out.println("running main stats service method in StatsDataLoader!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		SpringApplication.run(StatisticsDataLoader.class, args);
	}
	
	@Inject
    public StatisticsDataLoader(FileStatsDao statsProvider, StatisticsRepository statsRepository) {
		this.statsProvider=statsProvider;
		this.statsRepository=statsRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		//comment out this method if we just want to run the rest service and not load the data into the repository
		loadDataIntoMongo();
		System.exit(0);
	}

	private void loadDataIntoMongo() {
		List<Statistics>stats=statsProvider.getAllStatsFromFiles();
		System.out.println("stats size="+stats.size());
		this.saveDataToMongo(stats);
	}
	
	private void saveDataToMongo(List<Statistics>stats) {
		
		
		System.out.println("deleting data from mongodb!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		statsRepository.deleteAll();
		System.out.println("saving data to mongodb!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		statsRepository.saveAll(stats);//save in old spring saveall in new spring
	}

}
