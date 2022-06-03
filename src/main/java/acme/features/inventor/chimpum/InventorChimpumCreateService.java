package acme.features.inventor.chimpum;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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

		request.unbind(entity, model,"code", "title", "description", "creationMoment", "startDate",
										"finishDate","budget", "link");


	}

	@Override
	public Chimpum instantiate(final Request<Chimpum> request) {
		assert request != null;

		Chimpum result;
		result = new Chimpum();
		
		result.setCreationMoment(new Date());

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

		
		//En caso de que el code y el creationMoment estén duplicados:
		if (!errors.hasErrors("code")) {
			List<Chimpum> chimpumsWithSameCode;
			int numberOfChimpumWithCode;

			chimpumsWithSameCode = this.repository.findChimpumsWithSameCode(entity.getCode());

			numberOfChimpumWithCode = 0;
			for(final Chimpum chimpum: chimpumsWithSameCode) {	//Solo comparamos fecha (hora no): no usamos sql
				if(chimpum.getPattern().equals(entity.getPattern()))
					numberOfChimpumWithCode+= 1;
			}

			errors.state(request, numberOfChimpumWithCode == 0, "code", "inventor.chimpum.form.error.duplicated-code");
		}
		
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
		
		if(!errors.hasErrors("startDate")) {
			Calendar calendar;

			calendar = new GregorianCalendar();
			calendar.setTime(entity.getCreationMoment());
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			
			final Boolean condition = entity.getStartDate().after(calendar.getTime());

			errors.state(request, condition, "startDate", "inventor.chimpum.form.error.start-date");	
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

			errors.state(request, errorState, "finishDate", "inventor.chimpum.form.error.finish-date");	
		}
		
		if(!errors.hasErrors("budget")) {
			
			final String currency = entity.getBudget().getCurrency();
			final String currencyAvaliable = this.repository.acceptedCurrencies();
			
			boolean acceptedCurrency = false;

			for(final String cur: currencyAvaliable.split(",")) {
				acceptedCurrency = cur.trim().equalsIgnoreCase(currency);
				
				if(acceptedCurrency) break;
			}
			
			final Boolean valorPositivo = entity.getBudget().getAmount() > 0;
			
			errors.state(request, valorPositivo, "budget", "inventor.chimpum.form.error.negative-budget");
			errors.state(request, acceptedCurrency, "budget", "inventor.chimpum.form.error.negative-currency");
		}
		
		


	}

	@Override
	public void create(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}



}
