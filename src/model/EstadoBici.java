package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table (name="estadobici")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name="descripcion")
public abstract class EstadoBici implements Serializable{
	
	@Id @GeneratedValue	
	private long id;
	
	public EstadoBici() {

	}

	public long getId() {
		return id;
	}
	
	public abstract String getDescripcion();	
}