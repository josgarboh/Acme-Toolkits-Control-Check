package acme.features.inventor.plaba;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.plaba.Plaba;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import features.SpamDetector;

@Service
public class InventorPlabaCreateService  implements AbstractCreateService<Inventor,Plaba>{	

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

		request.bind(entity, errors, "code", "subject", "summary", "creationMoment", "startDate",
										"finishDate","income", "moreInfo");

	}

	@Override
	public void unbind(final Request<Plaba> request, final Plaba entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model,"code", "subject", "summary", "creationMoment", "startDate",
										"finishDate","income", "moreInfo");


	}

	@Override
	public Plaba instantiate(final Request<Plaba> request) {
		assert request != null;

		Plaba result;
		result = new Plaba();
		
		result.setCreationMoment(new Date());

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

		
		//En caso de que el code y el creationMoment estén duplicados:
		if (!errors.hasErrors("code")) {
			List<Plaba> PlabasWithSameCode;
			int numberOfPlabaWithCode;

			PlabasWithSameCode = this.repository.findPlabasWithSameCode(entity.getCode());

			numberOfPlabaWithCode = 0;
			for(final Plaba Plaba: PlabasWithSameCode) {	//Solo comparamos fecha (hora no): no usamos sql
				if(Plaba.getPattern().equals(entity.getPattern()))
					numberOfPlabaWithCode+= 1;
			}

			errors.state(request, numberOfPlabaWithCode == 0, "code", "inventor.plaba.form.error.duplicated-code");
		}
		
		if(!errors.hasErrors("subject")) {
			
			//Comprobacion de que el subject contiene palabras malsonantes (suaves o duras)
			
			final Boolean condition = !spamDetector.containsSpam(weakSpamTerms.split(","),
														weakSpamThreshold,
														entity.getSubject())
								&& !spamDetector.containsSpam(strongSpamTerms.split(","),
														strongSpamThreshold,
														entity.getSubject());
			
			errors.state(request, condition, "subjects", "inventor.plaba.form.error.spam-detector");
		}

		if(!errors.hasErrors("summary")) {
			
			//Comprobacion de que el summary contiene palabras malsonantes (suaves o duras)
			
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
			
			errors.state(request, valorPositivo, "income", "inventor.plaba.form.error.negative-income");
			errors.state(request, acceptedCurrency, "income", "inventor.plaba.form.error.negative-currency");
		}
		
		


	}

	@Override
	public void create(final Request<Plaba> request, final Plaba entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}



}
