package org.mousephenotype.cda.file.stats;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import uk.ac.ebi.phenotype.stats.dao.FileStatsDao;
import uk.ac.ebi.phenotype.stats.model.Statistics;
import uk.ac.ebi.phenotype.stats.utilities.SolrClientForStatsDecoration;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@TestPropertySource("file:${user.home}/configfiles/${profile:dev}/test.properties")
public class FileStatsDaoCategoricalTest {

	private final Logger logger = LoggerFactory.getLogger(FileStatsDaoCategoricalTest.class);
//chart for this set here: http://www.mousephenotype.org/data/charts?accession=MGI:1915747&parameter_stable_id=IMPC_HEM_038_001
	String center="MARC";
	String procedure="IMPC_HEM";
	String parameter="IMPC_HEM_038_001";
	String colonyId="1110018G07Rik_HEPD0633_2_C09_1";
	String zygosity="homozygote";
	String metadata="08aa37a898ab923b9ffdbd01c0077040";

	// String Configuration class
	// Only wire up the observation service for this test suite
	@Configuration
	@ComponentScan(
		basePackages = {"org.mousephenotype.cda"},
		useDefaultFilters = false,
		includeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {FileStatsDao.class})
		})
	static class ContextConfiguration {

		@NotNull
		@Value("${root_stats_directory:/data}")
		private String rootStatsDirectory;
		@NotNull
		@Value("${original_stats_directory:/data}")
		private String originalStatsDirectory;

		@Bean
		public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
			return new PropertySourcesPlaceholderConfigurer();
		}

	}

	//@Autowired
	FileStatsDao fileExperimentDao=new FileStatsDao();
	List<Statistics> categorical =null;
	Statistics categoricalStats1 =null;
	@Before
	public void setUp(){
		List<String> succesfulOnly=new ArrayList<>();
		succesfulOnly.add("/Users/jwarren/Documents/data/statsTestFiles/categorical/output_Successful.tsv");
		categorical = fileExperimentDao.getAllStatsFromFiles(null, null, succesfulOnly);
		SolrClientForStatsDecoration client=new SolrClientForStatsDecoration();
		client.decorateStatsWithImpressKeys(categorical);
		categoricalStats1 = categorical.get(0);

	}

	@Test
	public void testTopLevel(){
		System.out.println("result = "+ categorical);
		assertTrue(categorical.size()==1);
		assertNotNull(categoricalStats1.getParameterStableId());
		assertTrue(categoricalStats1.getParameterStableId().contains("IMPC_"));
		assertTrue(categoricalStats1.getProcedureStableId().contains("_"));
	}

	@Test
	public void testCategoricalData(){
		///nfs/nobackup/spot/mouseinformatics/HAMED_HA/DR10.1/jobs/Results_PhenStatAgeing_DR10.1_V1/JAX/IMPC_EYE/IMPC_EYE_001_001/JR18355/homozygote/08a8ef5de0bf12367da188d4ea030620
		//https://www.ebi.ac.uk/~hamedhm/windowing/DR10.1/jobs/Results_PhenStatAgeing_DR10.1_V1/JAX/IMPC_EYE/IMPC_EYE_001_001/JR18355/homozygote/08a8ef5de0bf12367da188d4ea030620/
		///Users/jwarren/Documents/data/statsTestFiles/categorical/output_Successful.tsv


	}
	


}
