package test;

import java.util.Date;
import junit.framework.TestCase;
import model.*;
import daos.*;

public class TestHistorial extends TestCase {

	static FactoryDAO FDAO; 
	static EstacionDAO estacionDao; 
	static Estacion e; 
	static Operativa op;
	static EstadoDAO estadoDao;
	static EstadoBiciDAO estadoBiciDao;
	static BicicletaDAO bicicletaDao;
	static HistorialDAO hDao;
	
	protected void setUp() throws Exception {
		super.setUp();
	    FDAO = new FactoryDAO();
	    FDAO.vaciarTablas();
	    estacionDao = FDAO.getEstacionDAO();
	    estadoDao = FDAO.getEstadoDAO();
	    estadoBiciDao = FDAO.getEstadoBiciDAO();
	    hDao = FDAO.getHistorialDAO();
	    bicicletaDao = FDAO.getBicicletaDAO();
	    estadoDao.altaEstado(new Operativa());
	    estacionDao.altaEstacion(new Estacion("Estacion1", "2.64684864,3.64684864", 12, estadoDao.buscarPorDescripcion("Operativa"), "1900"));
	    estadoBiciDao.altaEstadoBici(new AptaUso());
	    estadoBiciDao.altaEstadoBici(new EnDesuso());
	    estadoBiciDao.altaEstadoBici(new Denunciada());
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		FDAO.vaciarTablas();
	}
	
	public void testListados(){
		assertEquals(0,hDao.listar().size());
		assertEquals(0,hDao.listarParaBicicleta(1).size());
		bicicletaDao.altaBicicleta(new Bicicleta(new Date(), estacionDao.buscarPorNombre("Estacion1")));
		bicicletaDao.altaBicicleta(new Bicicleta(new Date(), estacionDao.buscarPorNombre("Estacion1"), estadoBiciDao.buscarPorDescripcion("Denunciada")));
		assertEquals(2,hDao.listar().size());
		assertEquals(1,hDao.listarParaBicicleta(1).size());
		assertEquals(1,hDao.listarParaBicicleta(2).size());
		bicicletaDao.cambiarEstadoBicicleta(2, estadoBiciDao.buscarPorDescripcion("En desuso"));
		assertEquals(2,hDao.listarParaBicicleta(2).size());
		assertEquals(3,hDao.listar().size());
	}
	
	public void testBuscarUltimo(){
		bicicletaDao.altaBicicleta(new Bicicleta(new Date(), estacionDao.buscarPorNombre("Estacion1")));
		String estado = hDao.buscarUltimo(1).getEstado().getDescripcion();
		assertEquals("Apta para uso", estado);
		bicicletaDao.cambiarEstadoBicicleta(1, estadoBiciDao.buscarPorDescripcion("En desuso"));
		estado = hDao.buscarUltimo(1).getEstado().getDescripcion();
		assertEquals("En desuso", estado);
		bicicletaDao.cambiarEstadoBicicleta(1, estadoBiciDao.buscarPorDescripcion("Denunciada"));
		estado = hDao.buscarUltimo(1).getEstado().getDescripcion();
		assertEquals("Denunciada", estado);
	}
	
	public void testAltaHistorial(){
		bicicletaDao.altaBicicleta(new Bicicleta(new Date(), estacionDao.buscarPorNombre("Estacion1")));
		bicicletaDao.altaBicicleta(new Bicicleta(new Date(), estacionDao.buscarPorNombre("Estacion1"), estadoBiciDao.buscarPorDescripcion("Denunciada")));
		assertEquals(2,hDao.listar().size());
		assertEquals("Apta para uso",hDao.listarParaBicicleta(1).get(0).getEstado().getDescripcion());
		assertEquals("Denunciada",hDao.listarParaBicicleta(2).get(0).getEstado().getDescripcion());
	}
}

