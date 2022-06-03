package acme.features.inventor.plaba;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.plaba.Plaba;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorPlabaShowService implements AbstractShowService<Inventor, Plaba>{
	
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
	public Plaba findOne(final Request<Plaba> request) {
		assert request != null;
	
		Integer plabaId;
		plabaId = request.getModel().getInteger("id");
		
		Plaba plaba;
		plaba = this.repository.findPlabaById(plabaId);
		
		return plaba;
	}

	@Override
	public void unbind(final Request<Plaba> request, final Plaba entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "creationMoment", "subject", "summary", "startDate",
										"finishDate", "income", "moreInfo");
		model.setAttribute("code", entity.getPattern());
		model.setAttribute("plabaId", entity.getId());
	}
	
}
