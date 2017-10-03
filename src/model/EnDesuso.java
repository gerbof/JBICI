package model;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("En desuso")
public class EnDesuso extends EstadoBici {

	public EnDesuso() {

	}
	
	public String getDescripcion(){
		return "En desuso";
	}

}
