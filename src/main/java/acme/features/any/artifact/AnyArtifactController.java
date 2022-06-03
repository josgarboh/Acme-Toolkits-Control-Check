package acme.features.any.artifact;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.artifacts.Artifact;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Any;

@Controller
public class AnyArtifactController extends AbstractController<Any, Artifact> {
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyArtifactListPublishedService		listService;

	@Autowired
	protected AnyArtifactShowService		showService;
	
	@Autowired
	protected AnyArtifactListToolkitService		listToolkitService;
	
	//Individual
	
	@Autowired
	protected AnyArtifactListWithChimpumService			listWithChimpumService;
	
	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		
		super.addCommand("list-published","list",this.listService);
		super.addCommand("list-toolkit","list" ,this.listToolkitService);
		
		super.addCommand("list-with-chimpum", "list", this.listWithChimpumService);
		
		super.addCommand("show", this.showService);
	}
}
