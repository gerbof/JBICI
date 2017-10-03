package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import model.Bicicleta;
import model.Estacion;
import model.Estado;
import model.EstadoBici;
import daos.FactoryDAO;

@ManagedBean (name="controllerEstaciones")
@RequestScoped
public class ControllerEstaciones{
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private final FactoryDAO factoryDao;
    private Estacion estacion;
    private String nombre;
    private String ubicacion;
    private Integer cantBici;
    private Integer cantLibres;
    private String estado;
    private String codPostal;
 
     
    public ControllerEstaciones() 
    {
    	factoryDao = new FactoryDAO();
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
    }
     
    public List<Estacion> listStation(){
    	return factoryDao.getEstacionDAO().listar();
    }
    
   public String registro(String valor,String station){
   	switch (valor){
   	case "alta":
   		httpServletRequest.getSession().setAttribute("operacionRegistro", "alta");
   		break;
   	case "edicion":
   		httpServletRequest.getSession().setAttribute("operacionRegistro", "edicion");
   		httpServletRequest.getSession().setAttribute("estacion", station);
   		estacion = factoryDao.getEstacionDAO().buscarPorNombre(station);
   		nombre = estacion.getNombre();
   		ubicacion = estacion.getUbicacion();
   		cantBici = estacion.getCantBicicletas();
   		cantLibres = estacion.getCantLugaresLibres();
   		codPostal = estacion.getCodPostal();
   		estado = estacion.getEstado().getDescripcion(); 		
   		break;
   	}
   	return "altaEstacion";
   }
   
   public ArrayList<SelectItem> getEstados() {
	   ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
	    List<Estado> estados = factoryDao.getEstadoDAO().listar();
	    
	    for (Estado e: estados)
	    	lista.add(new SelectItem(e.getDescripcion(),e.getDescripcion()));
	    
	    return lista;
	}
   
   public String altaEstacion(){   
	   estacion = new Estacion(nombre, ubicacion, cantLibres, factoryDao.getEstadoDAO().buscarPorDescripcion(estado), codPostal);	
	   factoryDao.getEstacionDAO().altaEstacion(estacion);
	   return "TableEstaciones";
   }
   
   public String editarEstacion(){  
	   estacion = new Estacion(nombre, ubicacion, cantLibres, factoryDao.getEstadoDAO().buscarPorDescripcion(estado), codPostal);
	   estacion.setCantBicicletas(cantBici);
	   factoryDao.getEstacionDAO().modificarEstacion(estacion, (String)httpServletRequest.getSession().getAttribute("estacion"));
	   return "TableEstaciones";
   }
   
  	public Estacion getEstacion() {
		return estacion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
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
	
	public Integer getCantBici() {
		return cantBici;
	}
	
	public void setCantBici(Integer cantBici) {
		this.cantBici = cantBici;
	}
	
	public Integer getCantLibres() {
		return cantLibres;
	}
	
	public void setCantLibres(Integer cantLibres) {
		this.cantLibres = cantLibres;
	}
	
	public String getCodPostal() {
		return codPostal;
	}
	
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
