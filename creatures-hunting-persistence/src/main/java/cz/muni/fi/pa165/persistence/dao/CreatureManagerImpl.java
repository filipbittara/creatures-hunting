package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Filip Bittara
 */
@Repository
public class CreatureManagerImpl implements CreatureManager {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Creature findCreature(Long id) {
        return em.find(Creature.class, id);
    }

    @Override
    public void addCreature(Creature creature) {
        em.persist(creature);
    }

    @Override
    public void deleteCreature(Creature creature) {
        em.remove(creature);
    }

    @Override
    public void updateCreature(Creature creature) {
        if(em.find(Creature.class, creature.getId()) == null) {
            throw new IllegalArgumentException("Creature could not be found in DB");
        }
        em.merge(creature);
    }

    @Override
    public List<Creature> findAllCreatures() {
        return em.createQuery("select c from Creature c", Creature.class)
				.getResultList();
    }

    @Override
    public Creature findCreatureByName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Cannot search for null name");
        try {
            return em.createQuery("c from Creature c where name=:name", Creature.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }   
    }
}
