package acme.features.inventor.plaba;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.plaba.Plaba;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorPlabaListService implements AbstractListService<Inventor, Plaba>{
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorPlabaRepository repository;

	// AbstractListService<Inventor, Plaba> interface ---------------------------
	
	@Override
	public boolean authorise(final Request<Plaba> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Plaba> findMany(final Request<Plaba> request) {
		assert request != null;
		
		Collection<Plaba> result;
		result = this.repository.findAllPlabas();
		
		return result;
		
	}

	@Override
	public void unbind(final Request<Plaba> request, final Plaba entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "creationMoment", "subject");
		model.setAttribute("code", entity.getPattern());
		
	}

}
