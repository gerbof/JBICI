package model;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Cerrada")
public class Cerrada extends Estado {

	public Cerrada() {

	}

	public String getDescripcion(){
		return "Cerrada";
	}
}
