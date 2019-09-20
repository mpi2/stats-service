package uk.ac.ebi.phenotype.stats;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import uk.ac.ebi.phenotype.stats.dao.FileStatsDao;
import uk.ac.ebi.phenotype.stats.dao.StatisticsRepository;
import uk.ac.ebi.phenotype.stats.model.Statistics;
import uk.ac.ebi.phenotype.stats.utilities.SolrClientForStatsDecoration;

//@ComponentScan("uk.ac.ebi.phenotype.stats.dao, uk.ac.ebi.phenotype.stats.utilities")
@SpringBootApplication
public class StatisticsDataLoader implements CommandLineRunner {
	
	
//	private String center;
//	
//	private String parameter;
	
	//@Autowired
	private StatisticsRepository statsRepository;
	private FileStatsDao statsProvider;
	
	private SolrClientForStatsDecoration solrClient;

	
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
    public StatisticsDataLoader(FileStatsDao statsProvider, StatisticsRepository statsRepository, SolrClientForStatsDecoration solrClient) {
		this.statsProvider=statsProvider;
		this.statsRepository=statsRepository;
		this.solrClient=solrClient;
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
		parser.accepts("index_file_path").withRequiredArg().ofType(String.class);
		parser.accepts("original_stats_directory").withOptionalArg().ofType(String.class);
		parser.accepts("root_stats_directory").withOptionalArg().ofType(String.class);
		parser.accepts("center").withOptionalArg().ofType(String.class);
		parser.accepts("parameter").withOptionalArg().ofType(String.class);
		OptionSet options = parser.parse(args);
		System.out.println("options="+options);

		if ( ! options.has("index_file_path")) {
			String message = "Missing required command-line parameter '-index_file_path'.";
			System.out.println(message);
			throw new RuntimeException(message);
		}
		String indexFilePath = (String) options.valuesOf("index_file_path").get(0);
		String originalStatDir="";
		String rootStatsDirectory="";
		String center="";//"MARC"; if not specified string will contain empty strings so as if not filtered at all by default
		String parameter="";//"IMPC_HEM_038_001";
		if ( options.has("original_stats_directory")) {
			originalStatDir = (String) options.valuesOf("original_stats_directory").get(0);
		}
		if ( options.has("root_stats_directory")) {
			rootStatsDirectory = (String) options.valuesOf("root_stats_directory").get(0);
		}
		if ( options.has("center")) {
			center = (String) options.valuesOf("center").get(0);
		}
		if ( options.has("parameter")) {
			parameter = (String) options.valuesOf("parameter").get(0);
		}
		

		boolean deleteFirst=false;
		
		////if(path.contains("IMPC_HEM_038_001")&& path.contains("MARC")) {
		if(deleteFirst) {
		System.out.println("deleting all data from mongodb!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		statsRepository.deleteAll();
		}
		
		List<String> successFilesOnly = statsProvider.readIndexFile(indexFilePath, originalStatDir, rootStatsDirectory);
		System.out.println("number of success files in index file="+successFilesOnly.size());
		loadDataIntoMongo(center, parameter, successFilesOnly);
		System.exit(0);
	}

	private void loadDataIntoMongo(String center, String parameter, List<String> successFilesOnly) {
		// lets batch the number of stats objects so that we don't use too much memory
		// on any given cluster node
		List<List<String>> smallerLists = chopped(successFilesOnly, 10);
		for (List<String> subList : smallerLists) {
			List<Statistics> stats = statsProvider.getAllStatsFromFiles(center, parameter, subList);
			if (!stats.isEmpty()) {
				System.out.println("stats size for saving to mongo=" + stats.size());
				List<Statistics> decoratedStats= solrClient.decorateStatsWithImpressKeys(stats);
				this.saveDataToMongo(stats);
			}
		}
	}

	private void saveDataToMongo(List<Statistics>stats) {
		System.out.println("saving data to mongodb!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		statsRepository.saveAll(stats);//save in old spring saveall in new spring
	}
	
	static <T> List<List<T>> chopped(List<T> list, final int L) {
	    List<List<T>> parts = new ArrayList<List<T>>();
	    final int N = list.size();
	    for (int i = 0; i < N; i += L) {
	        parts.add(new ArrayList<T>(
	            list.subList(i, Math.min(N, i + L)))
	        );
	    }
	    return parts;
	}

}
