package acme.features.inventor.artifact;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifacts.Artifact;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorArtifactListPlabaService implements AbstractListService<Inventor, Artifact>{
	
	//Individual
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorArtifactRepository repository;

	// AbstractListService<Inventor, Artifact> interface ---------------------------
	
	@Override
	public boolean authorise(final Request<Artifact> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Artifact> findMany(final Request<Artifact> request) {
		assert request != null;
		
		int plabaId;
		plabaId = request.getModel().getInteger("plaba");
		
		Collection<Artifact> result;
		result = this.repository.findPublishedArtifactsByPlabaId(plabaId);
		
		return result;
		
	}

	@Override
	public void unbind(final Request<Artifact> request, final Artifact entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "name", "retailPrice", "artifactType");
		
	}

}
