package controllers;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import model.*;
import daos.FactoryDAO;

@ManagedBean (name="controllerRegistro")
@RequestScoped
public class controllerRegistro{
	private String nombre;
    private String apellido;
    private String sexo;
    private String fecnac;
    private int perfil;
    private String domicilio;
    private String email;
    private String username;
    private String contrasenia;
    private String dni;
    private String activo;
   	private final FacesContext faceContext;
    private FacesMessage facesMessage;
    private final FactoryDAO factoryDao;
    private PerfilDeUsuario perfilClass;
    private Persona persona;
    private final HttpServletRequest httpServletRequest;
     
    public controllerRegistro() 
    {
    	factoryDao = new FactoryDAO();
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
    }
     
    public String altaUsuario()
    {	 
    	SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
    	Date fecha = null;
    	try {
    		fecha = formatoDelTexto.parse(fecnac);
    	} 
	    catch (Exception ex) 
	    {
	    	facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha Incorrecta", null);
            faceContext.addMessage(null, facesMessage);
	    }
    	persona = new Persona(dni,apellido,nombre,domicilio,fecha,sexo,email);
    	
    	/**
    	 * Generacion de password automatico*/
    	SecureRandom random = new SecureRandom();
    	contrasenia = new BigInteger(130, random).toString(7);
    	
    	String sessionUser = null;
    	
    	if (httpServletRequest.getSession().getAttribute("sessionUsuario") != null){
    		sessionUser = httpServletRequest.getSession().getAttribute("sessionUsuario").toString();
    	}
    	
    	if(sessionUser == null){    	
    		httpServletRequest.getSession().setAttribute("pass",contrasenia);
	    	perfilClass = new PerfilDeUsuario(persona, username, contrasenia);	
			factoryDao.getPerfilesDAO().altaPerfil(perfilClass);
    	}
    	else{
    		 String tipo=factoryDao.getPerfilesDAO().devolverTipo(sessionUser);
    		 if (tipo.equals("Administrador")) {
    			 username=email;
    			 contrasenia="1234";
    			 switch(this.getPerfil()){
    		 		case 1: perfilClass = new PerfilDeAdministrador(persona, username, contrasenia); break;
    		 		case 2: perfilClass = new PerfilDeUsuario(persona, username, contrasenia); break;
    			 }
    		 factoryDao.getPerfilesDAO().altaPerfil(perfilClass);
    		 }
    		 else{ return "VER DONDE VA"; /*CASO donde ya esta logueado y hace un modif de los datos*/}
    	}
    	perfilClass = factoryDao.getPerfilesDAO().buscarPorUsername(username);
    	
    	if(perfilClass!=null){
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil Creado Correctamente", null);
            faceContext.addMessage(null, facesMessage);
 
            
            if ((factoryDao.getPerfilesDAO().devolverTipo(sessionUser)!=null)&&(factoryDao.getPerfilesDAO().devolverTipo(sessionUser).equals("Administrador"))){
            	return "Table";
            }
            else {
            	return "PasswordView";
            }
        }
        else{
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Perfil NO creado", null);
            faceContext.addMessage(null, facesMessage);
            return "registerFail";
        }
    }
    
    public String edicionUsuario(){
    	httpServletRequest.getSession().setAttribute("operacionRegistro", "edicion");
    	PerfilDeUsuario perfil = factoryDao.getPerfilesDAO().buscarPorUsername(httpServletRequest.getSession().getAttribute("sessionUsuario").toString());
    	setNombre(perfil.getPersona().getNombres());
    	setApellido(perfil.getPersona().getApellido());
    	setDni(perfil.getPersona().getDni());
    	setDomicilio(perfil.getPersona().getDomicilio());
    	setEmail(perfil.getPersona().getEmail());
    	setContrasenia(perfil.getPassword());
    	setUsername(perfil.getUsername());
    	setActivo(perfil.getActivo().toString());
    	
    	String dia = (perfil.getPersona().getFechaNacimiento().toString()).substring(8,10);
    	String mes =  (perfil.getPersona().getFechaNacimiento().toString()).substring(5,7);
    	String anio = (perfil.getPersona().getFechaNacimiento().toString()).substring(0,4);
    	String fecmostrar = anio+"-"+mes+"-"+dia;
    	setFecnac(fecmostrar);
    	
    	switch(factoryDao.getPerfilesDAO().devolverTipo(perfil.getUsername())){
	 		case "Administrador": setPerfil(1); break;
	 		case "Usuario": setPerfil(2); break;
		 }
    	setSexo(perfil.getPersona().getSexo());
	    	
    	return "registerFail";
    }
    
    public String editarUsuario()
    {	
    	SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
    	Date fecha = null;
    	try {
    		fecha = formatoDelTexto.parse(fecnac);
    	} 
	    catch (Exception ex) 
	    {
	    	facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha Incorrecta", null);
            faceContext.addMessage(null, facesMessage);
	    }
    	
    	Persona persona2 = new Persona(dni,apellido,nombre,domicilio,fecha,sexo,email);
    	username = httpServletRequest.getSession().getAttribute("sessionUsuario").toString();
    	factoryDao.getPersonaDAO().modificarPersonaConDni(persona2, factoryDao.getPerfilesDAO().buscarPorUsername(username).getPersona().getDni());
    	persona = factoryDao.getPersonaDAO().buscarPorDni(dni);
    	switch(httpServletRequest.getSession().getAttribute("perfil").toString()){
	 		case "Administrador": perfilClass = new PerfilDeAdministrador(persona, username, contrasenia); break;
	 		case "Usuario": perfilClass = new PerfilDeUsuario(persona, username, contrasenia); break;
		 }
   	
    	factoryDao.getPerfilesDAO().modificarPerfilConUsername(perfilClass, username);
    	return edicionUsuario();
    } 
    
    
    public String getPasswordGenerado(){
    	return httpServletRequest.getSession().getAttribute("pass").toString();
    }
    
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getFecnac() {
		return fecnac;
	}

	public void setFecnac(String fecnac) {
		this.fecnac = fecnac;
	}

	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	 public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}
}
