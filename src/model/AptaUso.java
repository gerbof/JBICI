package model;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Apta para Uso")
public class AptaUso extends EstadoBici {

	public AptaUso() {
		
	}
	public String getDescripcion(){
		return "Apta para uso";
	}
}
