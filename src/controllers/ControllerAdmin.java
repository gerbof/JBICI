package controllers;

import java.util.List;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import DBLoader.BDLoader2;
import DBLoader.BDLoader3;
import model.Estacion;
import model.PerfilDeUsuario;
import daos.FactoryDAO;

@ManagedBean (name="controllerAdmin")
@SessionScoped
public class ControllerAdmin{
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private final FactoryDAO factoryDao;

    private String id;
	private String activo;
	private String page="/WEB-INF/facelets/AccessAdmin.xhtml";
     
    public ControllerAdmin() 
    {
    	factoryDao = new FactoryDAO();
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
    }
    
    public List<PerfilDeUsuario> listUsers(){
    	return factoryDao.getPerfilesDAO().listarUser();
    }
    
  
    public String actinact(){
    	
    	PerfilDeUsuario perfil = factoryDao.getPerfilesDAO().buscarPorUsername(this.getId());
    	
    	if(this.isActivo()=="true"){
    		factoryDao.getPerfilesDAO().inhabilitarUsuario(perfil, false);
    	}
    	else{
    		factoryDao.getPerfilesDAO().inhabilitarUsuario(perfil, true);
    	}
    	return "Table";
    }
    
    public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = "/WEB-INF/facelets/"+page+".xhtml";
	}
	
	public String devolverPag(String valor){
		return valor;
	}
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    public String isActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}
	
	public void ejecutarLoader(){
		new BDLoader2();
	}
	
	public String ejecutarLoader2(){
		Estacion estacion = factoryDao.getEstacionDAO().buscarPorNombre("Estacion Plaza Moreno");
		if(estacion == null){
			httpServletRequest.getSession().setAttribute("loader", "1");
			new BDLoader3();
		}
		else{
			httpServletRequest.getSession().setAttribute("loader", "0");
		}
		return devolverPag("loader");
	}
}
