package daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.*;

public class HistorialDAO implements InterfazHistorialDAO {

	static EMF emf = new EMF();
	static EntityManager em = emf.getEMF();
	static EstadoBiciDAO eDao;
	static BicicletaDAO bDao;
	static FactoryDAO FDAO;
	
	public HistorialDAO() {
		FDAO = new FactoryDAO();
		eDao = FDAO.getEstadoBiciDAO();
		bDao = FDAO.getBicicletaDAO();
	}
	
	public List<Historial> listar(){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Historial");
			@SuppressWarnings("unchecked")
			List<Historial> listado = (List<Historial>) q.getResultList();
			return listado;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return new ArrayList<Historial>();
	}
	
	public List<Historial> listarParaBicicleta(long idBicicleta){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select h from Historial h join h.bicicleta b where b.id = :id order by h.id desc");
			q.setParameter("id", idBicicleta);
			@SuppressWarnings("unchecked")
			List<Historial> listado = (List<Historial>) q.getResultList();
			return listado;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return new ArrayList<Historial>();
	}
	
	public Historial buscarUltimo(long idBicicleta){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			List<Historial> result = listarParaBicicleta(idBicicleta);
			if (!result.isEmpty()){
				return result.get(0);
			}
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public void altaHistorial(Historial historial){
		Bicicleta bici = bDao.buscar(historial.getBicicleta().getId());
		if (bici == null)
			return; 
		
		EstadoBiciDAO eDao = FDAO.getEstadoBiciDAO();
		EstadoBici es = eDao.buscarPorDescripcion(historial.getEstado().getDescripcion());
		if ( es == null){
			return; 
		}
		
		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			historial.setFechaHora(new Date());
			historial.setBicicleta(bici);
			historial.setEstado(es);
			em.persist(historial);
			etx.commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			if (etx != null) {
				etx.rollback();
			}
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
	}
	
	public void vaciarTabla(){
		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
			em.createNativeQuery("TRUNCATE Historial").executeUpdate();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
			etx.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			if (etx != null) {
				etx.rollback();
			}
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
	}
}
