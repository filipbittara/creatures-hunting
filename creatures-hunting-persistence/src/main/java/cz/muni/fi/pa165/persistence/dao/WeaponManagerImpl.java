package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.Weapon;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by kizivat on 28/10/15.
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
        em.remove(weapon);
    }

    @Override
    public void updateWeapon(Weapon weapon) {
        if (em.find(Weapon.class, weapon.getId()) == null) {
            throw new IllegalArgumentException("Weapon could not be found in DB.")
        }
    }

    @Override
    public List<Weapon> findAllWeapons() {
        return em.createQuery("select w from Weapon w", Weapon.class).getResultList();
    }
}