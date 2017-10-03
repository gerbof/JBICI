package test;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import model.Persona;

import daos.*;

public class TestPersona extends TestCase {

	static Persona p, p2, pEncontrada;
	static List<Persona> listado;
	static FactoryDAO FDAO; 
	static PersonaDAO pDao; 
	
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
		pDao = FDAO.getPersonaDAO();
		p = new Persona("12345678", "Perez", "Juan Alberto", "525 Nro 6589", fechaDate, "M", "pja@gmail.com");		
		p2 = new Persona("12345678", "Perez", "Juan Franciso Alberto", "525 Nro 6589", fechaDate, "M", "pja@gmail.com");
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		FDAO.vaciarTablas();
	}
	
	public void testListado() {
		List<Persona> listado = pDao.listar();
		assertEquals(0,listado.size());
	}
	
	public void testAlta() {
		pDao.altaPersona(p);
		List<Persona> listado = pDao.listar();
		assertEquals(1,listado.size());
		assertEquals("Perez", ((Persona)pDao.buscarPorDni("12345678")).getApellido());
		//Ver q no agrega dos veces a la persona
		pDao.altaPersona(p);
		listado = pDao.listar();
		assertEquals(1,listado.size());
	}
	
	public void testBuscar() {
		pDao.altaPersona(p);
		pEncontrada = pDao.buscarPorDni("12345678");
		assertEquals("Perez", pEncontrada.getApellido());
	}
	
	public void testModificar() {
		pDao.altaPersona(p);
		pDao.modificarPersonaConDni(p2, "12345678");
		assertNotEquals("Juan Alberto", ((Persona)pDao.buscarPorDni("12345678")).getNombres());
		assertEquals("Juan Franciso Alberto", ((Persona)pDao.buscarPorDni("12345678")).getNombres());
		pDao.modificarPersonaConDni(p2, "123456");
	}
	
	public void testBorrar() {
		pDao.altaPersona(p);
		//pDao.borrarPersona("12345678");
		pDao.borrarPersona(p);
		assertEquals(0, pDao.listar().size());
	}
}
