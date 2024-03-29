package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import cz.muni.fi.pa165.service.exception.ManagerDataAccessException;
import java.util.ArrayList;
import java.util.Collection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ondrej Klein
 */

@Service
public class WeaponServiceImpl implements WeaponService {
	
	@Autowired
	private WeaponManager weaponManager;
	
	@Autowired
	private CreatureManager creatureManager;

	@Override
	public Weapon findWeaponById(Long id) {
            try {
                return weaponManager.findWeapon(id);
            } catch (Exception e) {
                throw new ManagerDataAccessException("Error while retireving weapon", e);
            }
	}

	@Override
	public Weapon findWeaponByName(String name) {
            try {
                return weaponManager.findWeaponByName(name);
            } catch (Exception e) {
                throw new ManagerDataAccessException("Error while retireving weapon", e);
            }
	}

	@Override
	public Weapon addWeapon(Weapon weapon) {
		try {
                    weaponManager.addWeapon(weapon);
                } catch (Exception e) {
                    throw new ManagerDataAccessException("Error while creating weapon", e);
                }
		return weapon;
	}

	public void deleteWeapon(Weapon weapon) {
            try {
		weaponManager.deleteWeapon(weapon);
            } catch (Exception e) {
                throw new ManagerDataAccessException("Error while removing weapon", e);
            }
	}

	public void updateWeapon(Weapon weapon) {
            try {
                weaponManager.updateWeapon(weapon);
            } catch (Exception e) {
                throw new ManagerDataAccessException("Error while updating weapon", e);
            }
	}

	public List<Weapon> findAllWeapons() {
            try {
                return weaponManager.findAllWeapons();
            } catch (Exception e) {
                throw new ManagerDataAccessException("Error while retrieving weapons", e);
            }
	}

	@Override
	public List<Weapon> findWeaponsByCreature(Creature creature) {
		return new ArrayList<Weapon>(creature.getWeapons());
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
        
        

        public List<Weapon> getWeaponsToGoTroughAreas(Collection<Area> areas) {
            List<Weapon> result = new ArrayList<Weapon>();
            Set<Creature> creatures = new HashSet<Creature>();
            Set<Weapon> weapons = new HashSet<Weapon>();
            for (Area a : areas) {
                creatures.addAll(a.getCreatures());
            }
            
            ArrayList<Creature> toRemove = new ArrayList<>();
            for (Creature c : creatures) {
                if (c.getWeapons().size() < 1) {
                    toRemove.add(c);
                }
            }
            creatures.removeAll(toRemove);
        
            while(creatures.size() > 0) {
                for(Creature c : creatures) {
                    weapons.addAll(c.getWeapons());
                }
                int maxCount = 0;
                Weapon bestWeapon = null;
                for(Weapon w : weapons) {
                    Set<Creature> tempCreatures = w.getCreatures();
                    tempCreatures.retainAll(creatures);
                    if(tempCreatures.size() > maxCount) {
                        maxCount = w.getCreatures().size();
                        bestWeapon = w;
                    }
                }
                if(bestWeapon != null) {
                    creatures.removeAll(bestWeapon.getCreatures());
                    result.add(bestWeapon);
                } else {
                    //something went horribly wrong
                    break;
                }
            }
            return result;
        }

	
	
}
