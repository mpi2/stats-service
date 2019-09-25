package uk.ac.ebi.phenotype.stats;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import uk.ac.ebi.phenotype.stats.dao.FileStatsDao;
import uk.ac.ebi.phenotype.stats.dao.StatisticsRepository;
import uk.ac.ebi.phenotype.stats.model.Statistics;

import javax.inject.Inject;

@ComponentScan({"uk.ac.ebi.phenotype.stats.dao", "uk.ac.ebi.phenotype.stats.utilities"})
@SpringBootApplication
public class LineStatisticsDataLoader implements CommandLineRunner {


    //@Autowired
    private StatisticsRepository statsRepository;
    private FileStatsDao statsProvider;


    public static void main(String[] args) {

        new SpringApplicationBuilder(LineStatisticsDataLoader.class)
            .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
            .run(args);
        //SpringApplication.run(LineStatisticsDataLoader.class, args);
    }

    @Inject
    public LineStatisticsDataLoader(FileStatsDao statsProvider, StatisticsRepository statsRepository) {
        this.statsProvider = statsProvider;
        this.statsRepository = statsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean deleteFirst = false;
        String filePath = args[0];
        if (deleteFirst) {
            System.out.println("deleting all data from mongodb!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            statsRepository.deleteAll();
        }
        loadDataIntoMongo(filePath);
        System.exit(0);
    }

    private void loadDataIntoMongo(String filePath) {
        Statistics tempStats=null;
		try {
			tempStats = statsProvider.readSuccessFile(filePath);
		} catch (Exception e) {
			System.err.println("hit parsing error in one file with path"+filePath+" should continue without adding this result, setting tempStats to null...");
			tempStats=null;
			e.printStackTrace();
		}
        if(tempStats!=null) {
        statsRepository.save(tempStats);
        }
    }


}
