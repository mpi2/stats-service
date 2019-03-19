package org.mousephenotype.cda.file.stats;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "stats", path = "stats")
public interface StatsRepository extends MongoRepository<Stats, String> {

	List<Stats> findByParameterStableId(@Param("parameterId") String parameterId);
	
	//List<CellParameter> findByCellType(@Param("cellType") String cellType);

}
