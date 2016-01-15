package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.ChangeImageDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Ondrej Klein
 */
public interface WeaponFacade {
	/**
	 * Adds a weapon record.
	 *
	 * @param weapon instance of the weapon to be added
	 */
	public Long addWeapon(WeaponDTO weapon);

	/**
	 * Updates chosen weapon's column's values.
	 *
	 * @param weapon instance of the weapon to be updated featuring new data values
	 */
	public void updateWeapon(WeaponDTO weapon);

	/**
	 * Assigns a creature to a weapon.
	 *
	 * @param weapon weapon to assign
	 * @param creature creature to assign
	 */
	public void assignCreature(WeaponDTO weapon, CreatureDTO creature);

	/**
	 * Unlists a creature as being weak against the weapon.
	 *
	 * @param weapon weapon to have a creature unlisted
	 * @param creature creature to have a weapon unlisted
	 */
	public void removeCreature(WeaponDTO weapon, CreatureDTO creature);

	/**
	 * Removes a weapon record.
	 *
	 * @param weapon instance of the weapon to be removed
	 */
	public void removeWeapon(WeaponDTO weapon);

	/**
	 * Find a weapon by its ID in Weapons table.
	 *
	 * @param id ID of the weapon that is being searched for
	 * @return weapon with she sufficient ID
	 */
	public WeaponDTO getWeaponById(Long id);

	/**
	 * Find a weapon by its name in Weapons table.
	 *
	 * @param name name of the weapon that is being searched for
	 * @return weapon with she sufficient ID
	 */
	public WeaponDTO getWeaponByName(String name);

	/**
	 * Finds weapons that is the given creature weak against.
	 *
	 * @param creature creature to consider
	 * @return list of weapons the creature is weak against
	 */
	public List<WeaponDTO> getWeaponsByCreature(CreatureDTO creature);

	/**
	 * Lists all weapons.
	 *
	 * @return list of all weapons
	 */
	public List<WeaponDTO> getAllWeapons();
        
	/**
	 * Changes the weapon image
	 *
	 * @param imageChange new weapon image
	 */
        public void changeImage(ChangeImageDTO imageChange);
        
        public List<WeaponDTO> getWeaponsToGoTroughAreas(Collection<AreaDTO> areas);
}
