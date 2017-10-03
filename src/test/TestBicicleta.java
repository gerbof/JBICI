package test;

import java.util.Date;
import junit.framework.TestCase;
import model.*;
import daos.*;

public class TestBicicleta extends TestCase {

	static FactoryDAO FDAO; 
	static EstacionDAO pDao; 
	static Estacion e;
	static EnConstruccion ec;
	static Operativa op;
	static EstadoDAO eDao;
	static EstadoBiciDAO ebiciDao;
	static AptaUso a;
	static EnDesuso ed;
	static BicicletaDAO bDao;
	static Bicicleta b, b2;
	
	protected void setUp() throws Exception {
		super.setUp();
	    FDAO = new FactoryDAO(); 
	    FDAO.vaciarTablas();
		pDao = FDAO.getEstacionDAO(); 
		eDao = FDAO.getEstadoDAO();
		ebiciDao = FDAO.getEstadoBiciDAO();
		bDao = FDAO.getBicicletaDAO();
		op = new Operativa();
		ec = new EnConstruccion();
		eDao.altaEstado(op);
		eDao.altaEstado(ec);
		op = (Operativa) eDao.buscarPorDescripcion("Operativa");
		ec = (EnConstruccion) eDao.buscarPorDescripcion("En construccion");
		e = new Estacion("Estacion1", "2.64684864,3.64684864", 12, op, "1900");
		pDao.altaEstacion(e);
		e = pDao.buscarPorNombre("Estacion1");
		a = new AptaUso();
		ed = new EnDesuso();
		ebiciDao.altaEstadoBici(a);
		ebiciDao.altaEstadoBici(ed);
		a = (AptaUso) ebiciDao.buscarPorDescripcion("Apta para uso");
		ed = (EnDesuso) ebiciDao.buscarPorDescripcion("En desuso");
		b = new Bicicleta(new Date(), e, a);
		b2 = new Bicicleta(new Date(), e, ed);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		FDAO.vaciarTablas();
	}

	public void testListar(){
		assertEquals(0,bDao.listar().size());
		bDao.altaBicicleta(b);
		bDao.altaBicicleta(b2);
		assertEquals(2,bDao.listar().size());
		bDao.altaBicicleta(b2);
		assertEquals(2,bDao.listar().size());
	}

	
	public void testBuscar(){
		assertEquals(null,bDao.buscar(1));
		bDao.altaBicicleta(b2);
		assertEquals("En desuso",bDao.buscar(1).getEstadoActual().getDescripcion());
	}
	
	public void testAltaBicicleta(){
		assertEquals(0,bDao.listar().size());
		bDao.altaBicicleta(b);
		bDao.altaBicicleta(b2);
		assertEquals(2,bDao.listar().size());
		bDao.altaBicicleta(b2);
		assertEquals(2,bDao.listar().size());
		assertEquals("En desuso",bDao.buscar(2).getEstadoActual().getDescripcion());
	}
	
	public void testModificarBicicleta(){
		bDao.altaBicicleta(b2);
		assertEquals("En desuso",bDao.buscar(1).getEstadoActual().getDescripcion());
		bDao.modificarBicicleta(b, 1);
		assertEquals("Apta para uso",bDao.buscar(1).getEstadoActual().getDescripcion());
		assertEquals("Estacion1",bDao.buscar(1).getUbicacionActual().getNombre());
		e = new Estacion("Estacion2", "2.64684864,3.64684864", 12, op, "1900");
		pDao.modificarEstacion(e, "Estacion1");
		b.setUbicacionActual(e);
		bDao.modificarBicicleta(b, 1);
		assertEquals("Estacion2",bDao.buscar(1).getUbicacionActual().getNombre());
	}
	
	public void testCambiarEstadoBicicleta(){
		bDao.altaBicicleta(b2);
		assertEquals("En desuso",bDao.buscar(1).getEstadoActual().getDescripcion());
		bDao.cambiarEstadoBicicleta(1, a);
		assertEquals("Apta para uso",bDao.buscar(1).getEstadoActual().getDescripcion());
	}
	
	public void testBiciEnDesuso(){
		bDao.altaBicicleta(b);
		assertEquals("Apta para uso",bDao.buscar(1).getEstadoActual().getDescripcion());
		bDao.BiciEnDesuso(1);
		assertEquals("En desuso",bDao.buscar(1).getEstadoActual().getDescripcion());
	}
}

