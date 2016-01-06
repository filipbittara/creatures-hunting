/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ondrej Klein
 */
@Repository
public class AreaManagerImp implements AreaManager {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Area findArea(Long id) {
		return em.find(Area.class, id);
	}

	@Override
	public void addArea(Area a) {
		em.persist(a);
	}

	@Override
	public void deleteArea(Area a) {
		em.remove(em.contains(a) ? a : em.merge(a));
	}

	@Override
	public void updateArea(Area a) {
		em.merge(a);
	}

	@Override
	public List<Area> findAllAreas() {
		return em.createQuery("select a from Area a", Area.class)
				.getResultList();
	}

    @Override
    public Area findAreaByName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Cannot search for null name");
        try {
            return em.createQuery("select a from Area a where name=:name", Area.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }   
    }
        
        
	
}
