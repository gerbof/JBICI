package model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name="tipo")
@Table(name="perfiles")
@DiscriminatorValue("Usuario")
public class PerfilDeUsuario implements Serializable{

	@Id @GeneratedValue	
	private long id;
		
	@Column(nullable=false)
	private String username;
	@Column(nullable=false)
	private String password;
	private Boolean activo;
	
	@OneToOne(optional =false)
	@JoinColumn(name = "idPersona")
	private Persona persona;
	
	public PerfilDeUsuario(){

	}
	
	public PerfilDeUsuario(Persona usuario, String u, String p) {
		super();
		this.persona = usuario;
		this.username = u;
		this.password = p;
		this.activo = true;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona usuario) {
		this.persona = usuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public long getId() {
		return id;
	}
}
