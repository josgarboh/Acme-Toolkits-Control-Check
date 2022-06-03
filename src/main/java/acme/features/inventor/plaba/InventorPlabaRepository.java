package acme.features.inventor.plaba;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.artifacts.Artifact;
import acme.entities.plaba.Plaba;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorPlabaRepository extends AbstractRepository{

	@Query("select p from Plaba p")
	Collection<Plaba> findAllPlabas();

	@Query("select p from Plaba p where p.id = :id")
	Plaba findPlabaById(int id);
	
	@Query("select a from Artifact a where a.plaba.id = :id")
	Collection<Artifact> findArtifactsByPlabaId(int id);
	
	@Query("select p from Plaba p where p.code = :code")
	List<Plaba> findPlabasWithSameCode(String code);
	
	//Para validate
	
	@Query("select config.strongSpamTerms from ConfigData config")
	String findStrongSpamTerms();
	
	@Query("select config.weakSpamTerms from ConfigData config")
	String findWeakSpamTerms();
	
	@Query("select config.strongSpamTreshold from ConfigData config")
	int findStrongSpamTreshold();
	
	@Query("select config.weakSpamTreshold from ConfigData config")
	int findWeakSpamTreshold();
	
	@Query("select cd.acceptedCurrencies from ConfigData cd")
	String acceptedCurrencies();
}
