package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table (name="estado")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name="descripcion")
public abstract class Estado implements Serializable{
	
	@Id @GeneratedValue	
	private long id;
	
	public Estado(){

	}

	public long getId() {
		return id;
	}
	
	public abstract String getDescripcion();	
}
