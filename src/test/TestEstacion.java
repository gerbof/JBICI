package test;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import model.*;
import daos.*;

public class TestEstacion extends TestCase {

	static FactoryDAO FDAO; 
	static EstacionDAO pDao; 
	static Estacion e, e1; 
	static EnConstruccion ec;
	static Operativa op;
	static EstadoDAO eDao;
	static BicicletaDAO bDao;
	static EstadoBiciDAO esDao;
	
	protected void setUp() throws Exception {
		super.setUp();
	    FDAO = new FactoryDAO();
	    FDAO.vaciarTablas();
		pDao = FDAO.getEstacionDAO(); 
		eDao = FDAO.getEstadoDAO();
		op = new Operativa();
		ec = new EnConstruccion();
		eDao.altaEstado(op);
		eDao.altaEstado(ec);
		op = (Operativa) eDao.buscarEstado(1);
		ec = (EnConstruccion) eDao.buscarEstado(2);
		e = new Estacion("Estacion1", "2.64684864,3.64684864", 12, op, "1900");
		e1 = new Estacion("Estacion2", "2.64684864,3.64684864", 12, ec, "1900");
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		FDAO.vaciarTablas();
	}

	public void testListados() {
		assertEquals(0,pDao.listar().size());
		pDao.altaEstacion(e);
		pDao.altaEstacion(e1);
		List<Estacion> listado = pDao.listar();
		assertEquals(2,listado.size());
	}
	
	public void testAlta() {
		pDao.altaEstacion(e);
		pDao.altaEstacion(e);
		assertEquals(1,pDao.listar().size());
		assertEquals("Estacion1", pDao.buscarPorNombre("Estacion1").getNombre());
		assertEquals("Operativa", pDao.buscarPorNombre("Estacion1").getEstado().getDescripcion());
		pDao.altaEstacion(e1);
		assertEquals(2,pDao.listar().size());
		assertEquals("En construccion", pDao.buscarPorNombre("Estacion2").getEstado().getDescripcion());
	}
	
	public void testBuscar() {
		pDao.altaEstacion(e);
		pDao.altaEstacion(e1);
		assertEquals("Estacion1", pDao.buscarPorNombre("Estacion1").getNombre());
		assertNotEquals("Estacion1", pDao.buscarPorNombre("Estacion2").getNombre());
	}
	
	public void testModificar() {
		pDao.altaEstacion(e);
		assertEquals("Estacion1", pDao.buscarPorNombre("Estacion1").getNombre());
		assertEquals("Operativa", pDao.buscarPorNombre("Estacion1").getEstado().getDescripcion());
		pDao.modificarEstacion(e1, "Estacion1");
		assertEquals("Estacion2", pDao.buscarPorNombre("Estacion2").getNombre());
		assertEquals("En construccion", pDao.buscarPorNombre("Estacion2").getEstado().getDescripcion());
	}
	
	public void testCambiarEstado() {
		pDao.altaEstacion(e);
		Cerrada c = new Cerrada();
		eDao.altaEstado(c);
		pDao.cambiarEstadoEstacion("Estacion1",c);
		assertNotEquals("Operativa", pDao.buscarPorNombre("Estacion1").getEstado().getDescripcion());
		assertEquals("Cerrada", pDao.buscarPorNombre("Estacion1").getEstado().getDescripcion());
	}
	
	public void testBicicletasDisponibles(){
		bDao = FDAO.getBicicletaDAO();
		esDao = FDAO.getEstadoBiciDAO();
		pDao.altaEstacion(e);
		Bicicleta b1 = new Bicicleta(new Date(), e);
		Bicicleta b2 = new Bicicleta(new Date(), e);
		esDao.altaEstadoBici(new AptaUso());
		bDao.altaBicicleta(b1);
		bDao.altaBicicleta(b2);
		assertEquals(2, pDao.bicicletasDisponibles("Estacion1").size());
		
		esDao.altaEstadoBici(new Denunciada());
		bDao.cambiarEstadoBicicleta(1, esDao.buscarPorDescripcion("Denunciada"));
		assertEquals(1, pDao.bicicletasDisponibles("Estacion1").size());
		
		pDao.altaEstacion(e1);
		assertEquals(0, pDao.bicicletasDisponibles("Estacion2").size());
	}
	
	public void testCerrarEstacion(){
		pDao.altaEstacion(e);
		assertEquals("Operativa", pDao.buscarPorNombre("Estacion1").getEstado().getDescripcion());
		pDao.cerrarEstacion("Estacion1");
		assertEquals("Cerrada", pDao.buscarPorNombre("Estacion1").getEstado().getDescripcion());
	}
}

