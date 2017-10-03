package controllers;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import daos.FactoryDAO;

@ManagedBean (name="controllerSession")
@RequestScoped
public class controllerSession {

	private String usuario;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage facesMessage;
     
    public controllerSession() 
    {
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("sessionUsuario")!=null)
        {
            usuario=httpServletRequest.getSession().getAttribute("sessionUsuario").toString();
            httpServletRequest.getSession().setAttribute("perfil", (new FactoryDAO()).getPerfilesDAO().devolverTipo(usuario));
        }
    }
     
    public String cerrarSession()
    {
        httpServletRequest.getSession().removeAttribute("sessionUsuario");
        facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Session cerrada correctamente", null);
        faceContext.addMessage(null, facesMessage);
        httpServletRequest.getSession().invalidate();
        return "SessionClose";
    }
 
    public String getUsuario() {
        return usuario;
    }
 
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }    

}
