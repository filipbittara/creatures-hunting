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
	Weapon findWeaponById(Long id);
	Weapon findWeaponByName(String name);
	Set<Weapon> findWeaponsByCreature(Creature creature);
	void assignCreature(Weapon weapon, Creature creature);
	void removeCreature(Weapon weapon, Creature creature);
    Weapon addWeapon(Weapon weapon);
    void deleteWeapon(Weapon weapon);
    void updateWeapon(Weapon weapon);
    List<Weapon> findAllWeapons();
}
