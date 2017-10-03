package model;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("En construccion")
public class EnConstruccion extends Estado {

	public EnConstruccion() {

	}
	
	public String getDescripcion(){
		return "En construccion";
	}

}
