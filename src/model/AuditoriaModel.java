package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuditoriaModel {
	private String operacion;
	private Integer cantidad;
	
	public AuditoriaModel() {
		// TODO Auto-generated constructor stub
	}
	
	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
