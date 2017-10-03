package daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Estado;
import model.EstadoBici;
import model.Bicicleta;

public class EstadoBiciDAO implements InterfazEstadoBiciDAO {
	
	static EMF emf = new EMF();
	static EntityManager em = emf.getEMF();

	public List<EstadoBici> listar() {
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from EstadoBici");
			@SuppressWarnings("unchecked")
			List<EstadoBici> listado = (List<EstadoBici>) q.getResultList();
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

	public void altaEstadoBici(EstadoBici e){
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
	
	public void borrarEstadoBici(EstadoBici e){
		EstadoBici est = buscarPorDescripcion(e.getDescripcion());
		if( est != null){
			if(!em.isOpen())
				em = emf.getEMF();
			Query q = em.createQuery("select count(h) from Historial h join h.estado e where e.id = :id");
			q.setParameter("id", est.getId());
			long cant = (long) q.getSingleResult();
			if(cant==0){
				EntityTransaction etx = em.getTransaction();
				try{
					etx.begin();
					em.remove(em.contains(e) ? e : em.merge(e));
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
	
	public EstadoBici buscarEstado(long id) {
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from EstadoBici e where id = :id");
			q.setParameter("id", id);
			FactoryDAO.cerrarConexion(em);
			return (EstadoBici) q.getSingleResult();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}

	public EstadoBici buscarPorDescripcion(String desc){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from EstadoBici where descripcion = :desc");
			q.setParameter("desc", desc);
			List<EstadoBici> result = (List<EstadoBici>) q.getResultList();
			FactoryDAO.cerrarConexion(em);
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
	
	public void vaciarTabla(){
		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
			em.createNativeQuery("TRUNCATE EstadoBici").executeUpdate();
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
