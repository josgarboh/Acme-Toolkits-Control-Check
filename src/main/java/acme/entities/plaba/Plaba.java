package acme.entities.plaba;

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
public class Plaba extends AbstractEntity {
	
	// Serialisation identifier -----------------------------------------------
	
	private static final long serialVersionUID = 1L;
	
	//Attributes -----------------------------------
	
	@Column(unique = true)
	@Pattern(regexp = "^\\d{6}")
	@NotNull
	protected String 			code;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date 				creationMoment;
	
	@NotBlank
	@Length(max = 100)
	protected String 			subject;
	
	@NotBlank
	@Length(max = 255)
	protected String 			summary;
	
	//period:
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				finishDate;
	
	
	
	@NotNull
	@Valid
	protected Money 			income;
	
	@URL
	protected String 			moreInfo;
	
	//pattern -> Derivado de creationMoment y code
	
	public String getPattern() {
		String pattern;
		pattern = new SimpleDateFormat("dd/MM/yy").format(this.creationMoment);
		return this.code + ":" + pattern;
	}

}
