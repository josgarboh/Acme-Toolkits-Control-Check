package acme.entities.chimpum;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Chimpum extends AbstractEntity {
	
	// Serialisation identifier -----------------------------------------------
	
	private static final long serialVersionUID = 1L;
	
	//Attributes -----------------------------------
	
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	@NotNull
	protected String 			code;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date 				creationMoment;
	
	@NotBlank
	@Length(max = 100)
	protected String 			title;
	
	@NotBlank
	@Length(max = 255)
	protected String 			description;
	
	//Period
	
	//Un mes despues de la creación y una semana de largo (minimo)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				finishDate;
	
	//Valid -> (>0)
	@NotNull
	@Valid
	protected Money 			budget;
	
	@URL
	protected String 			link;	//opcional
	
	//pattern -> Derivado de creationMoment y code
	
	public String getPattern() {
		String pattern;
		pattern = new SimpleDateFormat("dd/MM/yy").format(this.creationMoment);
		return pattern + "-" + this.code;
	}

}
