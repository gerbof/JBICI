package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="bicicleta")
public class Bicicleta implements Serializable{

	@Id @GeneratedValue	
	private long id;
	@Column(nullable=false)
	private Date fechaIngreso;
	@OneToOne(optional = false)
	@JoinColumn(name="idEstacion")
	private Estacion ubicacionActual;
	@OneToOne(optional = false)
	@JoinColumn(name="idEstado")
	private EstadoBici estadoActual;
	
	public Bicicleta(){
		
	}
	
	public Bicicleta(Date fechaIngreso,
			Estacion ubicacionActual) {
		super();
		this.fechaIngreso = fechaIngreso;
		this.ubicacionActual = ubicacionActual;
		this.estadoActual = new AptaUso();
	}
	
	public Bicicleta(Date fechaIngreso,
			Estacion ubicacionActual, EstadoBici estado) {
		super();
		this.fechaIngreso = fechaIngreso;
		this.ubicacionActual = ubicacionActual;
		this.estadoActual = estado;
	}

	public long getId() {
		return id;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Estacion getUbicacionActual() {
		return ubicacionActual;
	}

	public EstadoBici getEstadoActual() {
		return estadoActual;
	}

	public void setUbicacionActual(Estacion ubicacionActual) {
		this.ubicacionActual = ubicacionActual;
	}
	
	public void editar(Date f, Estacion e){
		this.setFechaIngreso(f);
		this.setUbicacionActual(e);
	}
	
	public void cambiarEstado(EstadoBici estado) {
		this.estadoActual = estado;
	}
}
