package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="alquiler")
public abstract class Alquiler implements Serializable{

	@Id @GeneratedValue	
	private long id;
	@Column(nullable=false)
	private Date fechaHoraRetiro;
	
	@OneToOne(optional = false)
	@JoinColumn(name="idEstacionRetiro")
	private Estacion estacionRetiro;
	@OneToOne(optional = false)
	@JoinColumn(name="idBicicleta")
	private Bicicleta bicicleta;
	@OneToOne(optional = false)
	@JoinColumn(name="idPerfil")
	private PerfilDeUsuario perfil;

	public Alquiler(){
		
	}
	
	public Alquiler(Date fechaHoraRetiro,Estacion estacionRetiro, PerfilDeUsuario perfil) {
		super();
		this.fechaHoraRetiro = fechaHoraRetiro;
		this.estacionRetiro = estacionRetiro;
		this.bicicleta = null;
		this.perfil = perfil;
	}

	public Date getFechaHoraRetiro() {
		return fechaHoraRetiro;
	}

	public void setFechaHoraRetiro(Date fechaHoraRetiro) {
		this.fechaHoraRetiro = fechaHoraRetiro;
	}

	public Estacion getEstacionRetiro() {
		return estacionRetiro;
	}

	public void setEstacionRetiro(Estacion estacionRetiro) {
		this.estacionRetiro = estacionRetiro;
	}

	public Bicicleta getBicicleta() {
		return bicicleta;
	}

	public void setBicicleta(Bicicleta bicicleta) {
		this.bicicleta = bicicleta;
	}
	
	public abstract boolean activo();

	public long getId() {
		return id;
	}

	public PerfilDeUsuario getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilDeUsuario perfil) {
		this.perfil = perfil;
	}

}
