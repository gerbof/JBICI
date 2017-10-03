package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import daos.FactoryDAO;
import daos.AuditoriaDAO;
import model.AuditoriaModel;

@Path("/estadistica")
public class estadistica {

	AuditoriaDAO aDao = new FactoryDAO().getAuditoriaDAO();;

	public estadistica() {
		//BDLoader bd = new BDLoader(); 	
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AuditoriaModel> getEstadistica(@QueryParam("entidad") String entidad){
		return aDao.cantidadOperaciones(entidad);
	}
}
