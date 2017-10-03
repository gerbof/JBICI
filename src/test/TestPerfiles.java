package test;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import model.*;
import daos.*;

public class TestPerfiles extends TestCase {

	static Persona p1, p2, p3;
	static PerfilDeUsuario per1, per2;
	static PerfilDeAdministrador per3,per4;
	static List<PerfilDeUsuario> listado;
	static FactoryDAO FDAO; 
	static PerfilesDAO pDao; 
	
	protected void setUp() throws Exception {
		super.setUp();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	    Date fechaDate = null;
	    try {
	        fechaDate = formato.parse("23/12/1987");
	    } 
	    catch (Exception ex) 
	    {
	        System.out.println(ex);
	    }
	    FDAO = new FactoryDAO(); 
	    FDAO.vaciarTablas();
		pDao = FDAO.getPerfilesDAO(); 
		p1 = new Persona("12345678", "Perez", "Juan Alberto", "525 Nro 6589", fechaDate, "M", "pja@gmail.com");		
		p2 = new Persona("23456789", "Blanco", "Maria", "525 Nro 6589", fechaDate, "F", "bm@gmail.com");
		p3 = new Persona("89568965", "Gonzalez", "Martin", "525 Nro 6589", fechaDate, "M", "mg@gmail.com");
		per3 = new PerfilDeAdministrador(p1, "user1", "123456");
		per4 = new PerfilDeAdministrador(p2, "user2", "123456");
		per1 = new PerfilDeUsuario(p3, "user3", "123456");
		per2 = new PerfilDeUsuario(p1, "user4", "123456");
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		FDAO.vaciarTablas();
	}
	
	public void testListados() {
		List<PerfilDeUsuario> listado = pDao.listar();
		assertEquals(0,listado.size());
		pDao.altaPerfil(per1);
		pDao.altaPerfil(per2);
		pDao.altaPerfil(per3);
		listado = pDao.listar();
		assertEquals(3,listado.size());
		listado = pDao.listarUser();
		assertEquals(2,listado.size());
		assertEquals(1,pDao.listarAdmin().size());
	}
	
	public void testAlta() {
		pDao.altaPerfil(per1);
		List<PerfilDeUsuario> listado = pDao.listar();
		assertEquals(1,listado.size());
		//Controlar que no agregue al usuario mas de una vez con ese username
		per1 = new PerfilDeUsuario(p3, "user3", "123456");
		pDao.altaPerfil(per1);
		listado = pDao.listar();
		assertEquals(1,listado.size());
		per3 = new PerfilDeAdministrador(p3, "user3", "123456");
		pDao.altaPerfil(per3);
		listado = pDao.listar();
		assertEquals(1,listado.size());
		assertEquals("user3", ((PerfilDeUsuario)pDao.buscarPorUsername("user3")).getUsername());
		assertEquals("Gonzalez", ((PerfilDeUsuario)pDao.buscarPorUsername("user3")).getPersona().getApellido());
	}
	
	public void testBuscar() {
		pDao.altaPerfil(per1);
		assertEquals("Gonzalez", pDao.buscarPorUsername("user3").getPersona().getApellido());
	}
	
	public void testModificar() {
		pDao.altaPerfil(per1);
		pDao.modificarPerfilConUsername(per2, "user1");
		assertEquals("user3", pDao.buscarPorUsername("user3").getUsername());
		pDao.modificarPerfilConUsername(per2, "user3");
		assertNotEquals("user1", pDao.buscarPorUsername("user4").getUsername());
		assertEquals("Perez", pDao.buscarPorUsername("user4").getPersona().getApellido());
		pDao.modificarPerfilConUsername(per2, "user5");
	}
	
	public void testBorrar() {
		pDao.altaPerfil(per1);
		pDao.altaPerfil(per2);
		//pDao.borrarPerfil("user3");
		pDao.borrarPerfil(per1);
		assertEquals(1, pDao.listar().size());
		//new PersonaDAO().borrarPersona("12345678");
		new PersonaDAO().borrarPersona(p1);
		assertEquals(1, pDao.listar().size());
		//pDao.borrarPerfil("user4");
		pDao.borrarPerfil(per2);
		new PersonaDAO().borrarPersona(p1);
		//new PersonaDAO().borrarPersona("12345678");
		assertEquals(0, pDao.listar().size());
	}
	
	public void testDevolverPerfilesPersona() {
		pDao.altaPerfil(per1);
		pDao.altaPerfil(per2);
		per1 = new PerfilDeAdministrador(p3, "user5", "123456");
		pDao.altaPerfil(per1);
		assertEquals(2, pDao.devolverPerfilesPersona("89568965").size());
		assertEquals(0, pDao.devolverPerfilesPersona("23456789").size());
	}
	
	public void testBorrarPerfilesPersona(){
		pDao.altaPerfil(per3);
		pDao.altaPerfil(per2);
		//pDao.borrarPerfilesPersona("12345678");
		pDao.borrarPerfilesPersona(p1);
		assertEquals(0, pDao.listar().size());
	}
	
	public void testInahbilitarUsuario(){
		pDao.altaPerfil(per1);
		assertTrue(pDao.buscarPorUsername("user3").getActivo());
		//pDao.inhabilitarUsuario("user3", false);
		pDao.inhabilitarUsuario(per1, false);
		assertFalse(pDao.buscarPorUsername("user3").getActivo());
		//pDao.inhabilitarUsuario("user3", true);
		pDao.inhabilitarUsuario(per1, true);
		assertTrue(pDao.buscarPorUsername("user3").getActivo());
	}
}

