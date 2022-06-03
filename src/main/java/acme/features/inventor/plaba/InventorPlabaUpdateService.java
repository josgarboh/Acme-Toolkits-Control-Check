package acme.features.inventor.plaba;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.plaba.Plaba;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import features.SpamDetector;

@Service
public class InventorPlabaUpdateService implements AbstractUpdateService<Inventor, Plaba>{
	
	//Individual

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
	public void bind(final Request<Plaba> request, final Plaba entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "subject", "summary", "creationMoment", "startDate",
										"finishDate","income", "moreInfo");

	}

	@Override
	public void unbind(final Request<Plaba> request, final Plaba entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "subject", "summary", "creationMoment", "startDate",
										"finishDate","income", "moreInfo");
		model.setAttribute("code", entity.getPattern());
	}

	@Override
	public Plaba findOne(final Request<Plaba> request) {
		assert request != null;
	
		int plabaId;
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

		if(!errors.hasErrors("subject")) {
			
			final Boolean condition = !spamDetector.containsSpam(weakSpamTerms.split(","),
														weakSpamThreshold,
														entity.getSubject())
								&& !spamDetector.containsSpam(strongSpamTerms.split(","),
														strongSpamThreshold,
														entity.getSubject());
			
			errors.state(request, condition, "subject", "inventor.plaba.form.error.spam-detector");
		}

		if(!errors.hasErrors("summary")) {
			
			final Boolean condition = !spamDetector.containsSpam(weakSpamTerms.split(","),
														weakSpamThreshold,
														entity.getSummary())
								&& !spamDetector.containsSpam(strongSpamTerms.split(","),
														strongSpamThreshold,
														entity.getSummary());
			
			errors.state(request, condition, "summary", "inventor.plaba.form.error.spam-detector");
		}
		
		if(!errors.hasErrors("startDate")) {
			Calendar calendar;

			calendar = new GregorianCalendar();
			calendar.setTime(entity.getCreationMoment());
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			
			final Boolean condition = entity.getStartDate().after(calendar.getTime());

			errors.state(request, condition, "startDate", "inventor.plaba.form.error.start-date");	
		}
		
		if(!errors.hasErrors("finishDate")) {
			Calendar calendar;

			boolean errorState = true;

			if (entity.getStartDate() != null) {
				
				calendar = new GregorianCalendar();
				calendar.setTime(entity.getStartDate());
				calendar.add(Calendar.WEEK_OF_MONTH, 1);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				
				errorState = entity.getFinishDate().after(calendar.getTime());	//startDate debe ser previo (1 semana minimo)
			}

			errors.state(request, errorState, "finishDate", "inventor.plaba.form.error.finish-date");	
		}
		
		if(!errors.hasErrors("income")) {
			
			final String currency = entity.getIncome().getCurrency();
			final String currencyAvaliable = this.repository.acceptedCurrencies();
			
			boolean acceptedCurrency = false;

			for(final String cur: currencyAvaliable.split(",")) {
				acceptedCurrency = cur.trim().equalsIgnoreCase(currency);
				
				if(acceptedCurrency) break;
			}
			
			final Boolean valorPositivo = entity.getIncome().getAmount() > 0;
			
			errors.state(request, valorPositivo, "income", "inventor.Plaba.form.error.negative-income");
			errors.state(request, acceptedCurrency, "income", "inventor.Plaba.form.error.negative-currency");
		}
		
		


	}

	@Override
	public void update(final Request<Plaba> request, final Plaba entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
