package cz.muni.fi.pa165.service;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 *
 * @author Ondrej Klein
 */
@Service
public interface WeaponService {
	public Weapon findWeapon(Long id);
    public void addWeapon(Weapon weapon);
    public void deleteWeapon(Weapon weapon);
    public void updateWeapon(Weapon weapon);
    public List<Weapon> findAllWeapons();
}
