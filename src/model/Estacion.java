package model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="estacion")
public class Estacion implements Serializable{

	@Id @GeneratedValue	
	private long id;
	@Column(nullable=false)
	private String nombre;
	@Column(nullable=false)
	private String ubicacion;
	@Column(nullable=false)
	private Integer cantBicicletas;
	@Column(nullable=false)
	private Integer cantLugaresLibres;
	@OneToOne(optional=false)
	@JoinColumn(name="idEstado")
	private Estado estado;
	@Column(nullable=false)
	private String codPostal;
	
	public Estacion(){
		
	}
	
	public Estacion(String nombre, String ubicacion, Integer cantEstacionamientosLibres, Estado estado, String codPostal) {
		super();
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.cantBicicletas = 0;
		this.cantLugaresLibres = cantEstacionamientosLibres;
		this.estado = estado;
		this.codPostal = codPostal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Integer getCantBicicletas() {
		return cantBicicletas;
	}

	public void setCantBicicletas(Integer cantBicicletas) {
		this.cantBicicletas = cantBicicletas;
	}

	public Integer getCantLugaresLibres() {
		return cantLugaresLibres;
	}

	public void setCantLugaresLibres(Integer cantEstacionamientosLibres) {
		this.cantLugaresLibres = cantEstacionamientosLibres;
	}

	public Estado getEstado() {
		return estado;
	}

	public void cambiarEstado(Estado estado) {
		this.estado = estado;
	}
	
	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	
	public void cerrarEstacion(){
		this.cambiarEstado(new Cerrada());
	}
	
	public void editarEstacion(String nombre, String ubicacion, Integer cantBicicletas,
			Integer cantEstacionamientosLibres, Estado estado, String codPostal/*, List<Bicicleta> listBicis*/) {

		this.setNombre(nombre);
		this.setUbicacion(ubicacion);
		this.setCantBicicletas(cantBicicletas);
		this.setCantLugaresLibres(cantEstacionamientosLibres);
		this.cambiarEstado(estado);
		this.setCodPostal(codPostal);
	}

	public long getId() {
		return id;
	}
}
