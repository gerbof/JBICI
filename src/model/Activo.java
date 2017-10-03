package model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="activo")
@PrimaryKeyJoinColumn(name = "idAlquiler")
public class Activo extends Alquiler {
	
	public Activo(){
		
	}
	
	public Activo(Date fechaHora, Estacion estacionActual, PerfilDeUsuario perfil) {
		super(fechaHora, estacionActual, perfil);
	}
	
	public boolean activo(){
		return true;
	}
}
