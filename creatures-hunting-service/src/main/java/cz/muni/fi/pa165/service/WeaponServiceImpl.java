package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ondrej Klein
 */

@Service
public class WeaponServiceImpl implements WeaponService {
	
	@Inject
	private WeaponManager weaponManager;
	
	@Inject
	private CreatureManager creatureManager;

	@Override
	public Weapon findWeaponById(Long id) {
		return weaponManager.findWeapon(id);
	}

	@Override
	public Weapon findWeaponByName(String name) {
		return weaponManager.findWeaponByName(name);
	}

	@Override
	public Weapon addWeapon(Weapon weapon) {
		weaponManager.addWeapon(weapon);
		return weapon;
	}

	public void deleteWeapon(Weapon weapon) {
		weaponManager.deleteWeapon(weapon);
	}

	public void updateWeapon(Weapon weapon) {
		weaponManager.updateWeapon(weapon);
	}

	public List<Weapon> findAllWeapons() {
		return weaponManager.findAllWeapons();
	}

	@Override
	public Set<Weapon> findWeaponsByCreature(Creature creature) {
		return new HashSet<>(creature.getWeapons());
	}

	@Override
	public void assignCreature(Weapon weapon, Creature creature) {
		if (creature != null && weapon != null) {
			weapon.addCreature(creature);
			creature.addWeapon(weapon);
		} else {
			throw new IllegalArgumentException();
		}
		
	}

	@Override
	public void removeCreature(Weapon weapon, Creature creature) {
		if (creature != null && weapon != null) {
			weapon.removeCreature(creature);
		} else {
			throw new IllegalArgumentException();
		}
	}

	
	
}
