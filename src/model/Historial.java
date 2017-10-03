package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="historial")
public class Historial implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue	
	private long id;
	@Column(nullable=false)
	private Date fechaHora;
	
	@OneToOne(optional = false)
	@JoinColumn(name="idEstado")
	private EstadoBici estado; 
	
	@ManyToOne 
	@JoinColumn(name="idBicicleta")
	private Bicicleta bicicleta;
	
	public Historial(){
		
	}
	
	public Historial(Date fecha, EstadoBici estado, Bicicleta bicicleta) {
		super();
		this.fechaHora = fecha;
		this.estado = estado;
		this.bicicleta = bicicleta;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fecha) {
		this.fechaHora = fecha;
	}

	public EstadoBici getEstado() {
		return estado;
	}

	public void setEstado(EstadoBici estado) {
		this.estado = estado;
	}

	public long getId() {
		return id;
	}

	public Bicicleta getBicicleta() {
		return bicicleta;
	}

	public void setBicicleta(Bicicleta bicicleta) {
		this.bicicleta = bicicleta;
	}
}
