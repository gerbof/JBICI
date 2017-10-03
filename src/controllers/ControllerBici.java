package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import model.Activo;
import model.Bicicleta;
import model.Denunciada;
import model.Estacion;
import model.EstadoBici;
import model.Historico;
import daos.FactoryDAO;

@ManagedBean (name="controllerBici")
@RequestScoped
public class ControllerBici{
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private final FactoryDAO factoryDao;
    private Bicicleta bicicleta;
    private String estacion;
    private String fecha;
    private String estado;
     
    public ControllerBici() 
    {
    	factoryDao = new FactoryDAO();
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
    }
     
    public List<Bicicleta> listBicis(){
    	return factoryDao.getBicicletaDAO().listar();
    }
    
    public List<Activo> listBicisActuales(){
    	String usuario = (String) httpServletRequest.getSession().getAttribute("sessionUsuario");
    	List<Activo> activos = factoryDao.getAlquilerDAO().devolverActivoDePersona(usuario);	
    	return activos;	
    }
    
    public List<Historico> listHistorico(){
    	String usuario = (String) httpServletRequest.getSession().getAttribute("sessionUsuario");
    	return factoryDao.getAlquilerDAO().devolverHistoricoDePersona(usuario);		
    }
    
    public void denunciar(long id){
    	factoryDao.getBicicletaDAO().cambiarEstadoBicicleta(id, new Denunciada());
    }
    
   public String registro(String valor,long id){
   	switch (valor){
   	case "alta":
   		httpServletRequest.getSession().setAttribute("operacionRegistro", "alta");
   		break;
   	case "edicion":
   		httpServletRequest.getSession().setAttribute("operacionRegistro", "edicion");
   		httpServletRequest.getSession().setAttribute("id", id);
   		bicicleta = factoryDao.getBicicletaDAO().buscar(id);
   		estacion = bicicleta.getUbicacionActual().getNombre();
   		estado = bicicleta.getEstadoActual().getDescripcion();
   		break;
   	}
   	return "altaBici";
   }
   
   public ArrayList<SelectItem> getEstaciones() {
	   ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
	    List<Estacion> estaciones = factoryDao.getEstacionDAO().listarHabilitadas();
	    
	    for (Estacion estacion: estaciones)
	    	lista.add(new SelectItem(estacion.getNombre(),estacion.getNombre()));
	    
	    return lista;
	}
   
   public ArrayList<SelectItem> getEstados() {
	   ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
	    List<EstadoBici> estados = factoryDao.getEstadoBiciDAO().listar();
	    
	    for (EstadoBici estados2: estados)
	    	lista.add(new SelectItem(estados2.getDescripcion(),estados2.getDescripcion()));
	    
	    return lista;
	}
   
   public String altaBici(){   
	   bicicleta = new Bicicleta(new Date(), factoryDao.getEstacionDAO().buscarPorNombre(estacion), factoryDao.getEstadoBiciDAO().buscarPorDescripcion(estado));
	   factoryDao.getBicicletaDAO().altaBicicleta(bicicleta);	
	   return "TableBicicletas";
   }
   
   public String editarBici(){   
	   bicicleta = new Bicicleta(new Date(), factoryDao.getEstacionDAO().buscarPorNombre(estacion), factoryDao.getEstadoBiciDAO().buscarPorDescripcion(estado));
	   factoryDao.getBicicletaDAO().modificarBicicleta(bicicleta, (long)httpServletRequest.getSession().getAttribute("id"));	   
	   return "TableBicicletas";
   }
   
   public String editarEstadoBici(){   
	   factoryDao.getBicicletaDAO().cambiarEstadoBicicleta((long)httpServletRequest.getSession().getAttribute("id"),factoryDao.getEstadoBiciDAO().buscarPorDescripcion(estado));	   
	   return "TableBicicletas";
   }
   
   public String getEstacion() {
		return estacion;
	}

	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
