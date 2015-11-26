package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 *
 * @author Ondrej Klein
 */
@Service
public interface WeaponService {
	public Weapon findWeaponById(Long id);
	public Weapon findWeaponByName(String name);
	public List<Weapon> findWeaponsByCreature(Creature creature);
	public void assignCreature(Weapon weapon, Creature creature);
	public void removeCreature(Weapon weapon, Creature creature);
    public Weapon addWeapon(Weapon weapon);
    public void deleteWeapon(Weapon weapon);
    public void updateWeapon(Weapon weapon);
    public List<Weapon> findAllWeapons();
	
}
