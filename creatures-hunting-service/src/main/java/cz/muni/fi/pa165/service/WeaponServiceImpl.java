package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.entity.Weapon;

import java.util.List;

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

	public Weapon findWeapon(Long id) {
		return weaponManager.findWeapon(id);
	}

	public void addWeapon(Weapon weapon) {
		weaponManager.addWeapon(weapon);
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
	
}
