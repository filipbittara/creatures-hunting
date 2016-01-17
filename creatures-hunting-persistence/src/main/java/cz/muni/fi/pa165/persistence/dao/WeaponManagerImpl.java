package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author David Kizivat
 */
@Repository
public class WeaponManagerImpl implements WeaponManager {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Weapon findWeapon(Long id) {
        return em.find(Weapon.class, id);
    }

    @Override
    public void addWeapon(Weapon weapon) {
        em.persist(weapon);
    }

    @Override
    public void deleteWeapon(Weapon weapon) {
        em.remove(em.contains(weapon) ? weapon : em.merge(weapon));
    }

    @Override
    public void updateWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Weapon could not be null.");
        }
        if (em.find(Weapon.class, weapon.getId()) == null) {
            throw new IllegalArgumentException("Weapon could not be found in DB.");
        }
        em.merge(weapon);
        em.flush();
    }

    @Override
    public List<Weapon> findAllWeapons() {
        return em.createQuery("select w from Weapon w", Weapon.class).getResultList();
    }

    @Override
    public Weapon findWeaponByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null name");
        }
        try {
            return em.createQuery("select w from Weapon w where name=:name", Weapon.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
