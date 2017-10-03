package DBLoader;

import java.util.Date;

import javax.swing.JOptionPane;

import daos.*;
import model.*;

public class BDLoader {

	static FactoryDAO FDAO; 
	static EstacionDAO estacionDao; 
	static Estacion estacion; 
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
	static PerfilDeUsuario perfil;
	static AlquilerDAO alquilerDao;
	static Bicicleta bicicleta;
	static Alquiler alquiler;
	static Historico historico;
	
	public BDLoader() {
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
	    
	    estacion = new Estacion("Estacion1", "-34.910385, -57.954878", 12, operativa, "1900");
	    estacionDao.altaEstacion(estacion);
	    estacion = estacionDao.buscarPorNombre("Estacion1");
	    
	    bicicleta = new Bicicleta(new Date(), estacion);
	    bicicletaDao.altaBicicleta(bicicleta);
	    bicicleta = bicicletaDao.buscar(1);
	    
	    persona = new Persona("12345678", "Perez", "Juan Alberto", "525 Nro 6589", new Date(), "M", "pja@gmail.com");
	    personaDao.altaPersona(persona);
	    persona = personaDao.buscarPorDni("12345678");
	    
	    perfil = new PerfilDeUsuario(persona, "user1", "123456");
	    perfilDao.altaPerfil(perfil);
	    perfil = perfilDao.buscarPorUsername("user1");	  
	    
	    alquiler = new  Activo(new Date(), estacion, perfil);
	    historico = new Historico(alquiler.getFechaHoraRetiro(), new Date(), alquiler.getEstacionRetiro(), estacion, alquiler.getBicicleta(), alquiler.getPerfil());
	    
	    alquilerDao.altaActivo((Activo)alquiler);
	    alquilerDao.altaHistorico(alquiler.getId(), estacion);
	   
	    bicicleta = new Bicicleta(new Date(), estacion);
	    bicicletaDao.altaBicicleta(bicicleta);
	    bicicleta = bicicletaDao.buscar(2);
	    alquiler = new  Activo(new Date(), estacion, perfil);
	    alquilerDao.altaActivo((Activo)alquiler);
	}

	public static void main(String[] args) {
		if (JOptionPane.showConfirmDialog(null, "Desea cargar la BD ahora?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0){
			new BDLoader();
			JOptionPane.showMessageDialog(null, "Base de Datos cargada correctamente");
			
			if (JOptionPane.showConfirmDialog(null, "Desea vaciar la BD ahora?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0){
				(new FactoryDAO()).vaciarTablas(); 
				JOptionPane.showMessageDialog(null, "Ha vaciado la Base de Datos");
			}
			else{
				JOptionPane.showMessageDialog(null, "Omitio el vaciado de la Base de Datos");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Omitio la carga de la Base de Datos");
		}
	}

}
