package model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Denunciada")
public class Denunciada extends EstadoBici {

	public Denunciada() {

	}

	public String getDescripcion(){
		return "Denunciada";
	}
}
