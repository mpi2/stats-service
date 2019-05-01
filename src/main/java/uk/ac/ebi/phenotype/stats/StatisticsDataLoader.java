package uk.ac.ebi.phenotype.stats;

import java.util.List;

import javax.inject.Inject;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
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
		System.out.println("args="+args);
		for(String arg: args) {
			System.out.println("arg="+arg);
		}
		new SpringApplicationBuilder(StatisticsDataLoader.class)
        .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
        .run(args);
		//SpringApplication.run(StatisticsDataLoader.class, args);
	}
	
	@Inject
    public StatisticsDataLoader(FileStatsDao statsProvider, StatisticsRepository statsRepository) {
		this.statsProvider=statsProvider;
		this.statsRepository=statsRepository;
		statsProvider.readIndexFile();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("in run args="+args);
		for(String arg: args) {
			System.out.println("in run arg="+arg);
		}
		// Parse the command line options
		OptionParser parser = new OptionParser();

		parser.allowsUnrecognizedOptions();
		parser.accepts("filename").withRequiredArg().ofType(String.class);
		OptionSet options = parser.parse(args);
		System.out.println("options="+options);

		if ( ! options.has("filename")) {
			String message = "Missing required command-line parameter '-filename'.";
			System.out.println(message);
			throw new RuntimeException(message);
		}
		String indexFile = (String) options.valuesOf("filename").get(0);



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
