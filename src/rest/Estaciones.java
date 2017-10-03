package rest;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.Activo;
import model.Alquiler;
import model.Bicicleta;
import model.Denunciada;
import model.Estacion;
import model.Historico;
import model.PerfilDeUsuario;
import daos.AlquilerDAO;
import daos.FactoryDAO;
import daos.EstacionDAO;
import daos.PerfilesDAO;

@Path("/estacion")
public class Estaciones {

EstacionDAO eDao = new FactoryDAO().getEstacionDAO();
	
@GET
@Produces(MediaType.APPLICATION_JSON)
public List<Estacion> getEstaciones(){
	List<Estacion> listado = eDao.listar();
	if(!listado.isEmpty())
		return listado;
	else
		return null;
}

	
@GET
@Path("habilitadas")
@Produces(MediaType.APPLICATION_JSON)
public List<Estacion> getEstacionesHabilitadas(){
	List<Estacion> listado = eDao.listarHabilitadasMapa();
	if(!listado.isEmpty()){
		for(Estacion estacion : listado){
			List<Bicicleta> listado2 = eDao.bicicletasDisponibles(estacion.getNombre());
			int cantidad = 0;
			if(!listado2.isEmpty())
				cantidad = listado2.size();
			estacion.setCantBicicletas(cantidad);
		}
		return listado;
	}
	else{
		return null;

	}
}

@GET
@Path("disponibles")
public int disponibles(@QueryParam("nombre") String nombre){
	List<Bicicleta> listado = eDao.bicicletasDisponibles(nombre);
	if(!listado.isEmpty())
		return listado.size();
	else
		return 0;
}
	
@GET
@Path("retirar")
public void retirar(@QueryParam("nombre") String nombre,@QueryParam("username") String user){
	PerfilesDAO pDao = new FactoryDAO().getPerfilesDAO();
	AlquilerDAO alquilerDao = new FactoryDAO().getAlquilerDAO();
	PerfilDeUsuario perfil = pDao.buscarPorUsername(user);
	Alquiler alquiler = new  Activo(new Date(), eDao.buscarPorNombre(nombre), perfil);
	alquilerDao.altaActivo((Activo)alquiler);
}

@GET
@Path("estacionar")
public void estacionar(@QueryParam("nombre") String nombre,@QueryParam("username") String user){
	PerfilesDAO pDao = new FactoryDAO().getPerfilesDAO();
	AlquilerDAO alquilerDao = new FactoryDAO().getAlquilerDAO();
	List<Activo> activos = alquilerDao.devolverActivoDePersona(user);
	if (activos.isEmpty())
		return;
	long id = (activos.get(0)).getId();
	alquilerDao.altaHistorico(id, eDao.buscarPorNombre(nombre));
}

}
