package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import features.SpamDetector;

@Service
public class InventorChimpumCreateService  implements AbstractCreateService<Inventor,Chimpum>{	

	//Individual
	
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
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "code", "title", "description", "creationMoment", "startDate",
										"finishDate","budget", "link");

	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "title", "description", "creationMoment", "startDate",
										"finishDate","budget", "link");


	}

	@Override
	public Chimpum instantiate(final Request<Chimpum> request) {
		assert request != null;

		Chimpum result;
		result = new Chimpum();

		return result;
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		SpamDetector spamDetector;
		
		String strongSpamTerms;
		String weakSpamTerms;
		
		int strongSpamThreshold;
		int weakSpamThreshold;

		spamDetector = new SpamDetector();
		
		strongSpamTerms = this.repository.findStrongSpamTerms();
		weakSpamTerms = this.repository.findWeakSpamTerms();
		
		strongSpamThreshold = this.repository.findStrongSpamTreshold();
		weakSpamThreshold = this.repository.findWeakSpamTreshold();

		if(!errors.hasErrors("title")) {
			
			//Comprobacion de que el titulo contiene palabras malsonantes (suaves o duras)
			
			final Boolean condition = !spamDetector.containsSpam(weakSpamTerms.split(","),
														weakSpamThreshold,
														entity.getTitle())
								&& !spamDetector.containsSpam(strongSpamTerms.split(","),
														strongSpamThreshold,
														entity.getTitle());
			
			errors.state(request, condition, "title", "inventor.chimpum.form.error.spam-detector");
		}

		if(!errors.hasErrors("description")) {
			
			//Comprobacion de que la descripcion contiene palabras malsonantes (suaves o duras)
			
			final Boolean condition = !spamDetector.containsSpam(weakSpamTerms.split(","),
														weakSpamThreshold,
														entity.getDescription())
								&& !spamDetector.containsSpam(strongSpamTerms.split(","),
														strongSpamThreshold,
														entity.getDescription());
			
			errors.state(request, condition, "description", "inventor.chimpum.form.error.spam-detector");
		}


	}

	@Override
	public void create(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}



}
