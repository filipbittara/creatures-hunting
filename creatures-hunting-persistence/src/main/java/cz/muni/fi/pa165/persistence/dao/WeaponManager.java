package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.Weapon;

import java.util.List;

/**
 * Weapons data access object.
 *
 * @author David Kizivat
 * @version 0.1
 */
public interface WeaponManager {
    /**
     * Find a weapon by its ID in Weapons table.
     *
     * @param id ID of the weapon that is being searched for
     * @return weapon with she sufficient ID
     */
    public Weapon findWeapon(Long id);

    /**
     * Adds a weapon record.
     *
     * @param weapon instance of the weapon to be added
     */
    public void addWeapon(Weapon weapon);

    /**
     * Removes a weapon record.
     *
     * @param weapon instance of the weapon to be removed
     */
    public void deleteWeapon(Weapon weapon);

    /**
     * Updates chosen weapon's column's values.
     *
     * @param weapon instance of the weapon to be updated featuring new data values
     */
    public void updateWeapon(Weapon weapon);

    /**
     * Searches for all weapon records.
     *
     * @return a list of all weapons found
     */
    public List<Weapon> findAllWeapons();
}
