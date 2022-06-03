package acme.features.inventor.plaba;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.plaba.Plaba;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorPlabaController extends AbstractController<Inventor, Plaba>{

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorPlabaListService listService;
	
	@Autowired
	protected InventorPlabaShowService showService;
	
	@Autowired
	protected InventorPlabaCreateService createService;
	
	@Autowired
	protected InventorPlabaUpdateService updateService;

	@Autowired
	protected InventorPlabaDeleteService deleteService;
	
	// Constructors -----------------------------------------------------------
	
	@PostConstruct
	protected void initialise() {
		
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
		super.addCommand("delete", this.deleteService);
	}
	
}
