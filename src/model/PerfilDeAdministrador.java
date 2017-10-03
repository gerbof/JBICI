package model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Administrador")
public class PerfilDeAdministrador extends PerfilDeUsuario {

	public PerfilDeAdministrador(){

	}
	
	public PerfilDeAdministrador(Persona usuario, String u,	String p) {
		super(usuario, u, p);
	}
}
