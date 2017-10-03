package model;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("En reparacion")
public class EnReparacion extends EstadoBici {

	public EnReparacion() {

	}
	
	public String getDescripcion(){
		return "En reparacion";
	}

}
