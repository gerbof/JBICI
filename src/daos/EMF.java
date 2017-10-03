package daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("BD");
	private static EntityManager entity = null;
	
	public EMF(){
		if((entity==null)||(!entity.isOpen()))
			entity = emf.createEntityManager();
	}
	
	public EntityManager getEMF(){
		if((entity==null)||(!entity.isOpen()))
			return emf.createEntityManager();
		
		return entity;
	}
}
