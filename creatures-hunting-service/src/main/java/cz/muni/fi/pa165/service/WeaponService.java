package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 *
 * @author Ondrej Klein
 */

public interface WeaponService {
	/**
	 * Retrieves weapon with given id.
	 * @param id id of weapon to find
	 * @return weapon with given id
	 */
	Weapon findWeaponById(Long id);

	/**
	 * Finds weapon by its name
	 *
	 * @param name name of the weapon to be found
	 * @return weapon with given name
	 */
	Weapon findWeaponByName(String name);

	/**
	 * Finds weapons that is the given creature weak against.
	 *
	 * @param creature creature to consider
	 * @return list of weapons the creature is weak against
	 */
	List<Weapon> findWeaponsByCreature(Creature creature);

	/**
	 * Assigns a creature to a weapon.
	 *
	 * @param weapon weapon to assign
	 * @param creature creature to assign
	 */
	void assignCreature(Weapon weapon, Creature creature);

	/**
	 * Unlists a creature as being weak against the weapon.
	 *
	 * @param weapon weapon to have a creature unlisted
	 * @param creature creature to have a weapon unlisted
	 */
	void removeCreature(Weapon weapon, Creature creature);

	/**
	 * Creates a weapon.
	 *
	 * @param weapon instance of the weapon to be created
	 * @return instance of the weapon created
	 */
    Weapon addWeapon(Weapon weapon);

	/**
	 * Deletes a weapon.
	 *
	 * @param weapon instance of the weapon to be deleted
	 */
    void deleteWeapon(Weapon weapon);

	/**
	 * Updates the weapon info.
	 *
	 * @param weapon instance of the weapon having new info
	 */
    void updateWeapon(Weapon weapon);

	/**
	 * Lists all weapons.
	 *
	 * @return list of all weapons
	 */
    List<Weapon> findAllWeapons();
}
