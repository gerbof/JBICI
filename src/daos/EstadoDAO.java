package daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.*;



public class EstadoDAO implements InterfazEstadoDAO {
	
	static EMF emf = new EMF();
	static EntityManager em = emf.getEMF();
	

	public List<Estado> listar() {
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Estado");
			@SuppressWarnings("unchecked")
			List<Estado> listado = (List<Estado>)q.getResultList();
			return listado;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}

	public void altaEstado(Estado e){
		if ((this.buscarPorDescripcion(e.getDescripcion())) == null){
			if(!em.isOpen())
				em = emf.getEMF();
			EntityTransaction etx = em.getTransaction();
			try{
				etx.begin();
				em.persist(e);
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
	}
	
	public void borrarEstado(Estado e){
		Estado est = buscarPorDescripcion(e.getDescripcion());
		if( est != null){
			if(!em.isOpen())
				em = emf.getEMF();
			Query q = em.createQuery("select count(es) from Estacion es join es.estado e where e.id = :id");
			q.setParameter("id", est.getId());
			long cant = (long) q.getSingleResult();
			if(cant==0){
				EntityTransaction etx = em.getTransaction();
				try{
					etx.begin();	
					em.remove(em.contains(est) ? est : em.merge(est));
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
		}
	}

	public Estado buscarEstado(long id) {
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Estado e where id = :id");
			q.setParameter("id", id);
			return (Estado) q.getSingleResult();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}

	public void vaciarTabla(){
		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
			em.createNativeQuery("TRUNCATE Estado").executeUpdate();
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
	
	public Estado buscarPorDescripcion(String desc){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Estado where descripcion = :desc");
			q.setParameter("desc", desc);
			List<Estado> result = (List<Estado>) q.getResultList();
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
}
