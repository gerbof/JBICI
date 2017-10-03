package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="auditoria")
public class Auditoria {
	
	@Id @GeneratedValue	
	private long id;
	@Column(nullable=false)
	private String operacion; 
	@Column(nullable=false)
	private Date fechaHora;
	@Column(nullable=false)
	private String clase;
	@Column(nullable=false)
	private long idEntidad;
	
	public Auditoria() {
		// TODO Auto-generated constructor stub
	}
	
	public Auditoria(String operacion, String clase, long idEntidad) {
		super();
		this.operacion = operacion;
		this.fechaHora = new Date();
		this.clase = clase;
		this.idEntidad = idEntidad;
	}

	public long getId() {
		return id;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(long idEntidad) {
		this.idEntidad = idEntidad;
	}

}
