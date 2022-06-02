package acme.features.inventor.chimpum;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorChimpumShowService implements AbstractShowService<Inventor, Chimpum>{
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorChimpumRepository repository;

	// AbstractListService<Inventor, Chimpum> interface ---------------------------
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;		
		
		return true;
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;
	
		Integer chimpumId;
		chimpumId = request.getModel().getInteger("id");
		
		Chimpum chimpum;
		chimpum = this.repository.findChimpumById(chimpumId);
		
		return chimpum;
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "creationMoment", "title", "description", "startDate",
										"finishDate", "budget", "link");
		model.setAttribute("chimpumId", entity.getId());	//Para listar con artifacts
	}
	
}
