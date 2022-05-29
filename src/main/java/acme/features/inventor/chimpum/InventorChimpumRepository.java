package acme.features.inventor.chimpum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.artifacts.Artifact;
import acme.entities.chimpum.Chimpum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorChimpumRepository extends AbstractRepository{

	@Query("select c from Chimpum c")
	Collection<Chimpum> findAllChimpums();

	@Query("select c from Chimpum c where c.id = :id")
	Chimpum findChimpumById(int id);
	
	@Query("select a from Artifact a where a.chimpum.id = :id")
	Collection<Artifact> findArtifactsByChimpumId(int id);
	
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
