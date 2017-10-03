package test;

import java.util.Date;
import java.util.Map;

import junit.framework.TestCase;
import model.*;
import daos.*;

public class TestAlquiler extends TestCase {

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
	
	protected void setUp() throws Exception {
		super.setUp();
	    FDAO = new FactoryDAO();
	    estacionDao = FDAO.getEstacionDAO();
	    estadoDao = FDAO.getEstadoDAO();
	    estadoBiciDao = FDAO.getEstadoBiciDAO();
	    bicicletaDao = FDAO.getBicicletaDAO();
	    perfilDao = FDAO.getPerfilesDAO();
	    personaDao = FDAO.getPersonaDAO();
	    alquilerDao = FDAO.getAlquilerDAO();
	    FDAO.vaciarTablas();
	    
	    operativa = new Operativa();
		cerrada = new Cerrada();
		estadoDao.altaEstado(operativa);
		estadoDao.altaEstado(cerrada);
		operativa = (Operativa) estadoDao.buscarPorDescripcion("Operativa");
		cerrada = (Cerrada) estadoDao.buscarPorDescripcion("Cerrada");
		
		estadoBiciDao.altaEstadoBici(new AptaUso());
		estadoBiciDao.altaEstadoBici(new Denunciada());
		aptauso = (AptaUso) estadoBiciDao.buscarPorDescripcion("Apta para uso");
		denunciada = (Denunciada) estadoBiciDao.buscarPorDescripcion("Denunciada");
	    
	    estacion = new Estacion("Estacion1", "2.64684864,3.64684864", 12, operativa, "1900");
	    estacionDao.altaEstacion(estacion,1);
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
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		FDAO.vaciarTablas(); 
	}
	
	public void testAlquiler(){
		assertEquals(0, alquilerDao.listarActivo().size());
		assertEquals(0, alquilerDao.listarHistorico().size());
		
		alquilerDao.altaActivo((Activo)alquiler);
		
		assertEquals(alquiler.getId(), alquilerDao.listarActivo().get(0).getId());
		assertEquals(0, alquilerDao.listarHistorico().size());
		alquiler = alquilerDao.devolverActivo(1);
		assertEquals(alquiler.getId(),alquilerDao.devolverActivoDeBicicleta(bicicleta.getId()).get(0).getId());
		assertEquals(alquiler.getId(),alquilerDao.devolverActivoDeEstacionRetiro(estacion.getNombre()).get(0).getId());
		assertEquals(alquiler.getId(),alquilerDao.devolverActivoDePersona(perfil.getUsername()).get(0).getId());
		
		alquilerDao.altaHistorico(alquiler.getId(), estacion);
		assertEquals(0, alquilerDao.listarActivo().size());
		assertEquals(1, alquilerDao.listarHistorico().size());
		
		alquiler = (Historico)alquilerDao.listarHistorico().get(0);
		
		Historico acomparar = alquilerDao.devolverHistoricoDeBicicleta(bicicleta.getId()).get(0);
		assertEquals(alquiler.getId(),acomparar.getId());
		acomparar = alquilerDao.devolverHistoricoDeEstacionDevolucion(estacion.getNombre()).get(0);
		assertEquals(alquiler.getId(),alquilerDao.devolverHistoricoDeEstacionDevolucion(estacion.getNombre()).get(0).getId());
		acomparar = alquilerDao.devolverHistoricoDeEstacionRetiro(estacion.getNombre()).get(0);
		assertEquals(alquiler.getId(),acomparar.getId());
		acomparar = alquilerDao.devolverHistoricoDePersona(perfil.getUsername()).get(0);
		assertEquals(alquiler.getId(),acomparar.getId());
	}
}

