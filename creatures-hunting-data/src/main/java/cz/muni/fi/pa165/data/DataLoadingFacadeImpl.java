package cz.muni.fi.pa165.data;

import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.User;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import cz.muni.fi.pa165.persistence.entity.enums.AmmunitionType;
import cz.muni.fi.pa165.persistence.entity.enums.UserRole;
import cz.muni.fi.pa165.service.AreaService;
import cz.muni.fi.pa165.service.CreatureService;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Filip Bittara
 */
@Component
@Transactional
public class DataLoadingFacadeImpl implements DataLoadingFacade {

    
    @Autowired
    private CreatureService creatureService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private WeaponService weaponService;
    @Autowired
    private UserService userService;
    
    @Override
    public void loadData() {
        weapon("Knife", 1.0, AmmunitionType.NONE);
        weapon("Gun", 50.0, AmmunitionType.BULLET);
        user("filip", "filip", UserRole.USER, "fuck");
    }
    
    private Creature creature(String name, Double height, Double weight, Integer agility, Integer ferocity, String weakness) {
        Creature creature = new Creature();
        creature.setName(name);
        creature.setHeight(height);
        creature.setWeight(weight);
        creature.setAgility(agility);
        creature.setFerocity(ferocity);
        creature.setWeakness(weakness);
        creatureService.createCreature(creature);
        return creature;
    }
    
    private Area area(String name, String description, double latitude, double longitude) {
        Area area = new Area();
        area.setName(name);
        area.setDescription(description);
        area.setLatitude(latitude);
        area.setLongitude(longitude);
        areaService.createArea(area);
        return area;
    }
    
    private Weapon weapon(String name, Double gunReach, AmmunitionType ammunition) {
        Weapon weapon = new Weapon();
        weapon.setName(name);
        weapon.setGunReach(gunReach);
        weapon.setAmmunition(ammunition);
        weaponService.addWeapon(weapon);
        return weapon;
    }
    
    private User user(String username, String email, UserRole role, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);
        userService.registerUser(user, password);
        return user;
    }
}
