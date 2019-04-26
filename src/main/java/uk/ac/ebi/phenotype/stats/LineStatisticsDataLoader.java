package uk.ac.ebi.phenotype.stats;

import java.util.ArrayList;
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
public class LineStatisticsDataLoader implements CommandLineRunner {
	
	
	@Autowired
	private StatisticsRepository statsRepository;
	private FileStatsDao statsProvider;

	
	public static void main(String []args) {
		SpringApplication.run(LineStatisticsDataLoader.class, args);
	}
	
	@Inject
    public LineStatisticsDataLoader(FileStatsDao statsProvider, StatisticsRepository statsRepository) {
		this.statsProvider=statsProvider;
		this.statsRepository=statsRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		boolean deleteFirst=false;
		String filePath=args[0];
		if(deleteFirst) {
		System.out.println("deleting all data from mongodb!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		statsRepository.deleteAll();
		}
		loadDataIntoMongo(filePath);
		System.exit(0);
	}
	
	private void loadDataIntoMongo(String filePath) {
		List<Statistics>statsList=new ArrayList<>();
		Statistics tempStats=statsProvider.readSuccesFile(filePath);
		statsList.add(tempStats);
		this.saveDataToMongo(statsList);
	}

	
	private void saveDataToMongo(List<Statistics>stats) {
		System.out.println("saving data to mongodb!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		statsRepository.saveAll(stats);//save in old spring saveall in new spring
	}

}
