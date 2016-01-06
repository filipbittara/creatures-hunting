package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.ChangeImageDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.facade.WeaponFacade;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CreatureService;
import cz.muni.fi.pa165.service.WeaponService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ondrej Klein
 */

@Service
@Transactional
public class WeaponFacadeImpl implements WeaponFacade {

	@Autowired
	public WeaponService weaponService;

        @Autowired
        private CreatureService creatureService;
        
	@Autowired
        private BeanMappingService beanMappingService;

        public void setWeaponService(WeaponService weaponService) {
            this.weaponService = weaponService;
        }

        public void setBeanMappingService(BeanMappingService beanMappingService) {
            this.beanMappingService = beanMappingService;
        }


	@Override
	public Long addWeapon(WeaponDTO weapon) {
		Weapon newWeapon = weaponService.addWeapon(beanMappingService.mapTo(weapon, Weapon.class));
		return newWeapon.getId();
	}

	@Override
	public void updateWeapon(WeaponDTO weapon) {
		weaponService.updateWeapon(beanMappingService.mapTo(weapon, Weapon.class));
	}

	@Override
	public void assignCreature(WeaponDTO weapon, CreatureDTO creature) {
		weaponService.assignCreature(weaponService.findWeaponById(weapon.getId()), creatureService.getCreature(creature.getId()));
	}

	@Override
	public void removeCreature(WeaponDTO weapon, CreatureDTO creature) {
		weaponService.removeCreature(weaponService.findWeaponById(weapon.getId()), creatureService.getCreature(creature.getId()));

	}

	@Override
	public void removeWeapon(WeaponDTO weapon) {
		weaponService.deleteWeapon(beanMappingService.mapTo(weapon, Weapon.class));
	}

	@Override
	public WeaponDTO getWeaponById(Long id) {
		return beanMappingService.mapTo(weaponService.findWeaponById(id), WeaponDTO.class);
	}

	@Override
	public WeaponDTO getWeaponByName(String name) {
		return beanMappingService.mapTo(weaponService.findWeaponByName(name), WeaponDTO.class);
	}

	@Override
	public List<WeaponDTO> getWeaponsByCreature(CreatureDTO creature) {
		return beanMappingService.mapTo(weaponService.findWeaponsByCreature(creatureService.getCreature(creature.getId())), WeaponDTO.class);
	}

	@Override
	public List<WeaponDTO> getAllWeapons() {
		return beanMappingService.mapTo(weaponService.findAllWeapons(), WeaponDTO.class);
	}

	@Override
	public void changeImage(ChangeImageDTO imageChange) {
		Weapon w = weaponService.findWeaponById(imageChange.getId());
		w.setImage(imageChange.getImage());
		w.setImageMimeType(imageChange.getImageMimeType());
	}
}
