package acme.features.inventor.chimpum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.chimpum.Chimpum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorChimpumRepository extends AbstractRepository{

	@Query("select c from Chimpum c")
	Collection<Chimpum> findAllChimpums();

	@Query("select c from Chimpum c where c.id = :id")
	Chimpum findChimpumById(int id);
}
