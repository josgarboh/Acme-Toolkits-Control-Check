package acme.features.inventor.plaba;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifacts.Artifact;
import acme.entities.plaba.Plaba;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorPlabaDeleteService implements AbstractDeleteService<Inventor, Plaba>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorPlabaRepository repository;

	// AbstractDeleteService<Inventor, Plaba> interface ---------------------

	@Override
	public boolean authorise(final Request<Plaba> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Plaba> request, final Plaba entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationMoment", "subject", "summary", "startDate",
										"finishDate", "income", "moreInfo");
	}

	@Override
	public void unbind(final Request<Plaba> request, final Plaba entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "creationMoment", "subject", "summary", "startDate",
										"finishDate", "income", "moreInfo");
		model.setAttribute("code", entity.getPattern());
	}

	@Override
	public Plaba findOne(final Request<Plaba> request) {
		assert request != null;
	
		final int plabaId;
		plabaId = request.getModel().getInteger("id");
		
		Plaba result;	
		result = this.repository.findPlabaById(plabaId);

		return result;
	}

	@Override
	public void validate(final Request<Plaba> request, final Plaba entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Plaba> request, final Plaba entity) {
		assert request != null;
		assert entity != null;

		final Collection<Artifact> artifactsWithPlabaNotNull;
		artifactsWithPlabaNotNull = this.repository.findArtifactsByPlabaId(entity.getId());

		for(final Artifact artifact: artifactsWithPlabaNotNull) {
			artifact.setPlaba(null);
		}

		this.repository.saveAll(artifactsWithPlabaNotNull);
		this.repository.delete(entity);
	}

}
