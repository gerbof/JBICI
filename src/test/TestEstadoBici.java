package test;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import model.*;
import daos.*;

public class TestEstadoBici extends TestCase {
	static List<EstadoBici> listado;
	static FactoryDAO FDAO; 
	static EstadoBiciDAO eDao;
	static AptaUso a;
	static EnDesuso ed;
	static EnReparacion er;
	static Denunciada d;

	protected void setUp() throws Exception {
		super.setUp();
		FDAO = new FactoryDAO();
		FDAO.vaciarTablas();
		eDao = FDAO.getEstadoBiciDAO();
		a = new AptaUso();
		ed = new EnDesuso();
		er = new EnReparacion();
		d = new Denunciada();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		FDAO.vaciarTablas();
	}

	public void testAlta(){
		eDao.altaEstadoBici(a);
		assertEquals(1,eDao.listar().size());
		assertEquals("Apta para uso", (eDao.buscarPorDescripcion("Apta para uso").getDescripcion()));
		eDao.altaEstadoBici(d);
		assertEquals(2,eDao.listar().size());
		eDao.altaEstadoBici(d);
		assertNotEquals(3,eDao.listar().size());
	}
	
	public void testListado(){
		assertEquals(0,eDao.listar().size());
		eDao.altaEstadoBici(a);
		eDao.altaEstadoBici(d);
		eDao.altaEstadoBici(er);
		eDao.altaEstadoBici(ed);
		assertEquals(4,eDao.listar().size());
	}
	
	public void testBuscar(){
		eDao.altaEstadoBici(a);
		eDao.altaEstadoBici(d);
		eDao.altaEstadoBici(er);
		eDao.altaEstadoBici(ed);
		assertEquals("Apta para uso",(eDao.buscarPorDescripcion("Apta para uso").getDescripcion()));
		assertEquals("En desuso", (eDao.buscarPorDescripcion("En desuso").getDescripcion()));
		assertEquals("En reparacion", (eDao.buscarPorDescripcion("En reparacion").getDescripcion()));
		assertEquals("Denunciada", (eDao.buscarPorDescripcion("Denunciada").getDescripcion()));
	}
	
	public void testBorrar(){
		Operativa op = new Operativa();
		EstadoDAO esDao = FDAO.getEstadoDAO();
		esDao.altaEstado(op);
		op = (Operativa) esDao.buscarPorDescripcion("Operativa");
		EstacionDAO estDao = FDAO.getEstacionDAO();
		Estacion e = new Estacion("Estacion1", "2.64684864,3.64684864", 12, op, "1900");
		estDao.altaEstacion(e);
		e = estDao.buscarPorNombre("Estacion1");
		a = new AptaUso();
		eDao.altaEstadoBici(a);
		a = (AptaUso) eDao.buscarPorDescripcion("Apta para uso");
		Bicicleta b = new Bicicleta(new Date(), e, a);
		BicicletaDAO bDao = FDAO.getBicicletaDAO();
		bDao.altaBicicleta(b);
		b = bDao.buscar(1);
		eDao.altaEstadoBici(ed);
		assertEquals(2,eDao.listar().size());
		eDao.borrarEstadoBici(a);
		assertEquals(2,eDao.listar().size());
		eDao.borrarEstadoBici(ed);
		assertEquals(1,eDao.listar().size());
	}
}
