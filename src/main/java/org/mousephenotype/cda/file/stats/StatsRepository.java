package org.mousephenotype.cda.file.stats;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "stats", path = "stats")
public interface StatsRepository extends MongoRepository<Stats, String> {

	List<Stats> findAll();
	
	//http://localhost:8080/stats/search/findByGeneSymbolAndParameterStableId?geneSymbol=Arel1&parameterStableId=IMPC_HEM_038_001
	List<Stats> findByGeneSymbolAndParameterStableId(@Param("geneSymbol") String geneSymbol, @Param("parameterStableId") String parameterStableId);
	
	//http://localhost:8080/stats/search/findByGeneSymbol?geneSymbol=Arel1
	List<Stats> findByGeneSymbol(@Param("geneSymbol") String geneSymbol);
	
	
	//http://localhost:8080/stats/search/findByParameterStableId?parameterStableId=IMPC_HEM_038_001
	List<Stats> findByParameterStableId(@Param("parameterStableId") String parameterStableId);
	
	
	
	//List<CellParameter> findByCellType(@Param("cellType") String cellType);

}
