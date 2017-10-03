package DBLoader;

import java.util.Date;

import javax.persistence.EntityManager;

import daos.*;
import model.*;

public class BDLoader3 {

	static FactoryDAO FDAO; 
	static EstacionDAO estacionDao; 
	static Estacion estacion; 
	static Estacion estacion2;
	static Operativa operativa;
	static Cerrada cerrada; 
	static AptaUso aptauso;
	static Denunciada denunciada;
	static EstadoDAO estadoDao;
	static EstadoBiciDAO estadoBiciDao;
	static BicicletaDAO bicicletaDao;
	static PerfilesDAO perfilDao;
	static PersonaDAO personaDao;
	static Persona persona;
	static Persona persona2;
	static PerfilDeUsuario perfil;
	static PerfilDeUsuario perfil2;
	static AlquilerDAO alquilerDao;
	static Bicicleta bicicleta;
	static Alquiler alquiler;
	static Historico historico;
	
	public BDLoader3() {
		FDAO = new FactoryDAO();
	    estacionDao = FDAO.getEstacionDAO();
	    estadoDao = FDAO.getEstadoDAO();
	    estadoBiciDao = FDAO.getEstadoBiciDAO();
	    bicicletaDao = FDAO.getBicicletaDAO();
	    perfilDao = FDAO.getPerfilesDAO();
	    personaDao = FDAO.getPersonaDAO();
	    alquilerDao = FDAO.getAlquilerDAO();
	    
	    operativa = new Operativa();
		cerrada = new Cerrada();
		estadoDao.altaEstado(operativa);
		estadoDao.altaEstado(cerrada);
		estadoDao.altaEstado(new EnConstruccion());

		operativa = (Operativa) estadoDao.buscarPorDescripcion("Operativa");
		cerrada = (Cerrada) estadoDao.buscarPorDescripcion("Cerrada");
		
		estadoBiciDao.altaEstadoBici(new AptaUso());
		estadoBiciDao.altaEstadoBici(new Denunciada());
		estadoBiciDao.altaEstadoBici(new EnReparacion());
		estadoBiciDao.altaEstadoBici(new EnDesuso());
		aptauso = (AptaUso) estadoBiciDao.buscarPorDescripcion("Apta para uso");
		denunciada = (Denunciada) estadoBiciDao.buscarPorDescripcion("Denunciada");
	    
	    estacion = new Estacion("Estacion Plaza Moreno", "-34.921320, -57.954641", 12, operativa, "1900");
	    estacionDao.altaEstacion(estacion);
	    estacion = estacionDao.buscarPorNombre("Estacion Plaza Moreno");
	    
	    estacion2 = new Estacion("Estacion Plaza Italia", "-34.910623, -57.955327", 15, operativa, "1900");
	    estacionDao.altaEstacion(estacion2);
	    estacion2 = estacionDao.buscarPorNombre("Estacion Plaza Italia");
	    
	    bicicleta = new Bicicleta(new Date(), estacion);
	    bicicletaDao.altaBicicleta(bicicleta);
	    bicicleta = bicicletaDao.buscar(1);
	    
	    persona = new Persona("32548474", "Milleni", "Juan Alberto", "25 N° 6589", new Date(), "Masculino", "mja@gmail.com");
	    personaDao.altaPersona(persona);
	    persona = personaDao.buscarPorDni("32548474");
	       
	    perfil = new PerfilDeUsuario(persona, "user", "user");
	    perfilDao.altaPerfil(perfil);
	    perfil = perfilDao.buscarPorUsername("user");	  
	    
	    persona2 = new Persona("654321", "Perez", "Juan", "511 N° 69", new Date(), "Masculino", "yyy@gmail.com");
	    personaDao.altaPersona(persona2);
	    persona2 = personaDao.buscarPorDni("654321");
	    
	    perfil2 = new PerfilDeAdministrador(persona2, "admin", "admin");
	    perfilDao.altaPerfil(perfil2);
	    perfil2 = perfilDao.buscarPorUsername("admin");	  
	    
	    
	    alquiler = new  Activo(new Date(), estacion, perfil);
	    historico = new Historico(alquiler.getFechaHoraRetiro(), new Date(), alquiler.getEstacionRetiro(), estacion, alquiler.getBicicleta(), alquiler.getPerfil());
	    
	    alquilerDao.altaActivo((Activo)alquiler);
	    alquilerDao.altaHistorico(alquiler.getId(), estacion);
	   
	    bicicleta = new Bicicleta(new Date(), estacion);
	    bicicletaDao.altaBicicleta(bicicleta);
	    bicicleta = bicicletaDao.buscar(2);
	    alquiler = new  Activo(new Date(), estacion, perfil);
	    alquilerDao.altaActivo((Activo)alquiler);
	    
	    perfilDao.modificarPerfilConUsername(perfil, perfil.getUsername());
	    perfilDao.modificarPerfilConUsername(perfil, perfil.getUsername());
	    
	    perfilDao.inhabilitarUsuario(perfil, false);
	    perfilDao.inhabilitarUsuario(perfil, true);
	    
	    estacionDao.cambiarEstadoEstacion(estacion2.getNombre(), cerrada);
	    
		//estadoBiciDao.borrarEstadoBici(estadoBiciDao.buscarPorDescripcion("En desuso"));
		
		personaDao.modificarPersonaConDni(persona, persona.getDni());
		
		FDAO.getAuditoriaDAO().eliminarNulos();
	   
	}

}
