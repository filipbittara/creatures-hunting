package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import java.util.List;

/**
 *
 * @author Ondrej Klein
 */
public interface WeaponFacade {
	public Long addWeapon(WeaponDTO weapon);
	public void updateWeapon(WeaponDTO weapon);
	public void assignToCreature(WeaponDTO weapon, CreatureDTO creature);
	public void removeFromCreature(WeaponDTO weapon, CreatureDTO creature);
	public void removeWeapon(WeaponDTO weapon);
	public WeaponDTO getWeaponById(Long id);
	public WeaponDTO getWeaponByName(String name);
	public List<WeaponDTO> getWeaponsByCreature(CreatureDTO creature);
	public List<WeaponDTO> getAllWeapons();
}
