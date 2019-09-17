package uk.ac.ebi.phenotype.stats.utilities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class SolrClientForStatsDecorationTest {
    private SolrClientForStatsDecoration client=null;
    @Before
    public void setUp(){
        client=new SolrClientForStatsDecoration();
    }

    @Test
    public void getPipelineKey() {
        //client.getPipelineKey();

    }

    @Test
    public void getParameterKey() {
    }
}
