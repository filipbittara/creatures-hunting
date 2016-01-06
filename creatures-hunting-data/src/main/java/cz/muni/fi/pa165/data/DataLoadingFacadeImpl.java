package cz.muni.fi.pa165.data;

import cz.muni.fi.pa165.persistence.entity.enums.CreatureType;
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
        Creature bongun = creature("Bongun", CreatureType.UNDEAD, 43.2, 12.31, 9, 32, "Ice");
        creature("Anubis", CreatureType.UNDEAD, 183.9, 43.4, 12, 10, "Fire attacks");
        creature("Jockey", CreatureType.HUMANOID, 120.3, 30.0, 7, 8, "Small and stupid");
        creature("Witch", CreatureType.SPIRIT, 155.0, 45.0, 9, 9, "Loses stamina quickly when startled");
        creature("Wild dog", CreatureType.BEAST, 80.0, 35.0, 10, 10, "Wants to play fetch");
        creature("Friendly bear", CreatureType.UNKNOWN, 170.0, 60.0, 1, 1, "Wants to snuggle");
        Area stockade = area("The Central Stockade",
                "The Central Stockade is a soul prison located in the middle of Newborn City. " +
                        "It contains a number of especially dangerous demons and undeads, amongst " +
                        "them the infamous Anubis himself.",
                0.0, 0.0);
        area("Riverdown Valley",
                "Riverdown Valley is a one of the nicer zones covering the inhabitant's reactions to the " +
                        "Stonewrought Dam bursting during the Shattering and the resulting loss of water " +
                        "and environmental changes.",
                0.3, 1.31);
        area("Dun Morogh", "Dun Morogh is a snowy region located between the magma-strewn wasteland of the" + 
                " Searing Gorge to the south, the gentle ridges of Loch Modan to the east, and the swampy "+ 
                "Wetlands to the north. Dun Morogh is home to both the gnomes of Gnomeregan and the Ironforge"+
                " dwarves and is the location of the major city of Ironforge. The Khaz Mountains surround"+
                "Dun Morogh on all sides, making it accessible only by certain passes that are currently watched over by dwarven troops.", 
                2.2, 2.3);
        area("Rostok", "A desert oasis of sorts - a place completely devoid of anomalies and mutants. Be "+
                "sure to check out the 100 Rads bar in the vaults - it's a meeting point for stalkers from all over the Zone.", 
                3.0, 5.5);
        area("Great Swamp", "The marshlands consist mostly of murky irradiated water and tall reeds, with thin passages of safe"+
                " land stretching like labyrinthine bridges all over. A few abandoned buildings and settlements dot the landscape.",
                5.2, 6.0);
        area("Red Forest", "A dreadful place. Easy to vanish without a trace there, no matter how lucky you have been in life so far. "+
                "Whoever gets close to the Brain Scorcher immediately goes insane, turns into a zombie and starts stalking the Zone restlessly.",
                6.0, 2.0);
        Weapon knife = weapon("Knife", 1.0, AmmunitionType.NONE);
        weaponService.assignCreature(knife, bongun);
        areaService.addCreatureToArea(bongun, stockade);
        weapon("Shotgun", 20.0, AmmunitionType.SHELL);
        weapon("Chainsaw", 3.0, AmmunitionType.OTHER);
        weapon("Sixshooter", 30.0, AmmunitionType.BULLET);
        weapon("Baseball bat", 3.0, AmmunitionType.NONE);
        weapon("Nuclear grenade", 8000.0, AmmunitionType.OTHER);
        weapon("Tank", 200.0, AmmunitionType.BULLET);
        user("filip", "filip", UserRole.USER, "fuck");
        user("ondra", "a@b.c", UserRole.USER, "123");
        user("user", "user@user.cz", UserRole.USER, "user");
        user("admin", "admin@admin.cz", UserRole.ADMIN, "admin");
    }

    private Creature creature(String name, CreatureType type, Double height, Double weight, Integer agility, Integer ferocity, String weakness) {
        Creature creature = new Creature();
        creature.setType(type);
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
