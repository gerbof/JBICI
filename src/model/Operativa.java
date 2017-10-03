package model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Operativa")
public class Operativa extends Estado {

	public Operativa() {

	}
	
	public String getDescripcion(){
		return "Operativa";
	}
}
