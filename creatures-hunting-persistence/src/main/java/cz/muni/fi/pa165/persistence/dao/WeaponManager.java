package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.Weapon;

import java.util.List;

/**
 * @author David Kizivat
 */
public interface WeaponManager {
    public Weapon findWeapon(Long id);
    public void addWeapon(Weapon weapon);
    public void deleteWeapon(Weapon weapon);
    public void updateWeapon(Weapon weapon);
    public List<Weapon> findAllWeapons();
}
