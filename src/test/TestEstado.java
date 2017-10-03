package test;

import junit.framework.TestCase;
import java.util.List;
import model.*;
import daos.*;

public class TestEstado extends TestCase {
	static List<Estado> listado;
	static FactoryDAO FDAO; 
	static EstadoDAO eDao;
	static Cerrada c;
	static EnConstruccion ec;
	static Operativa op;
	static EstacionDAO pDao; 
	static Estacion e;

	protected void setUp() throws Exception {
		super.setUp();
		FDAO = new FactoryDAO();
		FDAO.vaciarTablas();
		eDao = FDAO.getEstadoDAO();
		c = new Cerrada();
		ec = new EnConstruccion();
		op = new Operativa();
		pDao = FDAO.getEstacionDAO();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		FDAO.vaciarTablas();
	}

	public void testListado(){
		List<Estado> listado = eDao.listar();
		assertEquals(0,listado.size());
		eDao.altaEstado(op);
		eDao.altaEstado(ec);
		eDao.altaEstado(c);
		listado = eDao.listar();
		assertEquals(3,listado.size());
	}
	
	public void testBuscar(){
		eDao.altaEstado(op);
		eDao.altaEstado(ec);
		eDao.altaEstado(c);
		assertEquals("Cerrada", (eDao.buscarPorDescripcion("Cerrada").getDescripcion()));
		assertEquals("Operativa", (eDao.buscarPorDescripcion("Operativa").getDescripcion()));
		assertEquals("En construccion", (eDao.buscarPorDescripcion("En construccion").getDescripcion()));
	}
	
	public void testAlta(){
		eDao.altaEstado(op);
		List<Estado> listado = eDao.listar();
		assertEquals(1,listado.size());
		eDao.altaEstado(op);
		listado = eDao.listar();
		assertEquals(1, listado.size());
		eDao.altaEstado(ec);
		assertEquals("En construccion", (eDao.buscarPorDescripcion("En construccion").getDescripcion()));
		assertEquals(2, eDao.listar().size());
	}
	
	public void testBorrar(){
		eDao.altaEstado(ec);
		eDao.altaEstado(c);
		eDao.borrarEstado(ec);
		assertEquals(1,eDao.listar().size());
		eDao.borrarEstado(c);
		assertEquals(0,eDao.listar().size());
		eDao.altaEstado(op);
		e = new Estacion("Estacion1", "2.64684864,3.64684864", 12, op, "1900");
		pDao.altaEstacion(e);
		eDao.borrarEstado(op);
		assertEquals(1,eDao.listar().size());
	}
}
