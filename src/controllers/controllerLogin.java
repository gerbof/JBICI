package controllers;

import javax.faces.bean.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import DBLoader.BDLoader3;
import model.PerfilDeUsuario;
import daos.EMF;
import daos.FactoryDAO;

@ManagedBean (name="controllerLogin")
@RequestScoped
public class controllerLogin{
	private String usuario;
    private String contrasenia;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage facesMessage;
    private final FactoryDAO factoryDao;
    private PerfilDeUsuario usuarioClass;
     
    public controllerLogin() 
    {
    	factoryDao = new FactoryDAO();
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
    }
     
    public String login()
    {	
    	String log = "failLogin";
    	usuarioClass = factoryDao.getPerfilesDAO().buscarPorUsername(usuario);
    	if((usuarioClass!=null) && (usuarioClass.getPassword().equals(contrasenia))){
            httpServletRequest.getSession().setAttribute("sessionUsuario", usuario);
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", null);
            faceContext.addMessage(null, facesMessage);
            
            String tipo = factoryDao.getPerfilesDAO().devolverTipo(usuarioClass.getUsername());
            switch (tipo){
            	case "Administrador": log =  "successLoginAdmin"; break;
            	case "Usuario": log = "successLoginUser"; break;
            }
            return log;
        }
        else{
        	if(usuarioClass == null){
        		facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario inexistente", null);
        	}
        	else{
        		facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña incorrecta", null);
        	}
        	faceContext.addMessage(null, facesMessage);
            return log;
        }
    }
    
    public String registro(String valor){
    	switch (valor){
    	case "registro":
    		httpServletRequest.getSession().setAttribute("operacionRegistro", null);
    		break;
    	case "alta":
    		httpServletRequest.getSession().setAttribute("operacionRegistro", "alta");
    		break;
    	case "edicion":
    		httpServletRequest.getSession().setAttribute("operacionRegistro", "edicion");
    		break;
    	}
    	return "registerFail";
    }
 
    public String getUsuario() {
        return usuario;
    }
 
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
 
    public String getContrasenia() {
        return contrasenia;
    }
 
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }  
    
    public void cargarLoader(){
    	new BDLoader3();
    }
}
