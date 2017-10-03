package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="historico")
@PrimaryKeyJoinColumn(name = "idAlquiler")
public class Historico extends Alquiler {
	
	@Column(nullable=true)
	private Date fechaHoraDevolucion;
	
	@OneToOne(optional = true)
	@JoinColumn (name = "idEstacionDevolucion")
	private Estacion estacionDevolucion;
	
	public Historico(){
		
	}
	
	public Historico(Date fechaHoraRetiro, Date fechaHoraDevolucion,
			Estacion estacionRetiro, Estacion estacionDevolucion,
			Bicicleta bicicleta, PerfilDeUsuario perfil) {
		super(fechaHoraRetiro, estacionRetiro, perfil);
		this.setBicicleta(bicicleta);
		this.setEstacionDevolucion(estacionDevolucion);
		this.setFechaHoraDevolucion(fechaHoraDevolucion);
	}
	
	public boolean activo(){
		return false;
	}
	
	public Date getFechaHoraDevolucion() {
		return fechaHoraDevolucion;
	}

	public void setFechaHoraDevolucion(Date fechaHoraDevolucion) {
		this.fechaHoraDevolucion = fechaHoraDevolucion;
	}

	public Estacion getEstacionDevolucion() {
		return estacionDevolucion;
	}

	public void setEstacionDevolucion(Estacion estacionDevolucion) {
		this.estacionDevolucion = estacionDevolucion;
	}
}
