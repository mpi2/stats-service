package org.mousephenotype.cda.file.stats;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.solr.server.support.HttpSolrClientFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import uk.ac.ebi.phenotype.stats.dao.FileStatsDao;
import uk.ac.ebi.phenotype.stats.model.Statistics;
import uk.ac.ebi.phenotype.stats.utilities.SolrClientForStatsDecoration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@TestPropertySource("file:${user.home}/configfiles/${profile:dev}/test.properties")
public class FileStatsDaoTest {

	private final Logger logger = LoggerFactory.getLogger(FileStatsDaoTest.class);
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

	@Test
	public void testUnidimensionalStatsFromLocalFile() {
		List<String> succesfulOnly=new ArrayList<>();
		succesfulOnly.add("/Users/jwarren/Documents/data/statsTestFiles/output_Successful.tsv");
		List<Statistics> stats = fileExperimentDao.getAllStatsFromFiles(null, null, succesfulOnly);
		System.out.println("result = "+stats);
		assertTrue(stats.size()==1);
		Statistics stats1 = stats.get(0);
		assertNotNull(stats1.getResult().getDetails());
		assertNotNull(stats1.getResult().getDetails().getExperimentDetail());
		assertNotNull(stats1.getResult().getDetails().getExperimentDetail().get("parameter_stable_id"));
		assertNotNull(stats1.getParameterStableId());
		assertTrue(stats1.getParameterStableId().contains("IMPC_"));
		assertTrue(stats1.getProcedureStableId().contains("_"));
		SolrClientForStatsDecoration client=new SolrClientForStatsDecoration();
		client.decorateStatsWithImpressKeys(stats);
		assertNotNull(stats1.getImpressProcedureKey());
		System.out.println("experiment details="+stats1.getResult().getDetails().getExperimentDetail());


	}

//	@Test
//	public void getFilePath(){
//		String filePath=fileExperimentDao.getFilePathFromIndex(center, procedure, parameter, colonyId, zygosity, metadata);
//		assertFalse(filePath.isEmpty());
//		
//		
//		
//	}
//	
//	@Test
//	public void getFileWhenNotInIndex() {
//		String filePath=fileExperimentDao.getFilePathFromIndex("blah", procedure, parameter, colonyId, zygosity, metadata);
//		assert(filePath.isEmpty());
//	}
	
//	@Test
//	public void readIndexFileTest() {
//		File indexFile=fileExperimentDao.readIndexFile();
//		assert(indexFile.isFile());
//	}
//
//	
//	@Test
//	public void getParameterOptionsForRequest() {
//		List<String> filePaths = fileExperimentDao.getParameterOptionsForRequest(center, parameter, metadata);
//		assert(filePaths.size()>0);
//		filePaths.forEach(blah -> System.out.println(blah));
//		System.out.println("filepaths size="+filePaths.size());
//		
//	}
//	
	


}
