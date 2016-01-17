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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        Creature bongun = creature("Bongun", CreatureType.UNDEAD, 43.2, 12.31, 9, 10, "Ice", "bongun.jpg");
        creature("Anubis", CreatureType.UNDEAD, 183.9, 43.4, 10, 10, "Fire attacks", "anubis.jpg");
        creature("Jockey", CreatureType.HUMANOID, 120.3, 30.0, 7, 8, "Small and stupid", "jockey.jpg");
        creature("Witch", CreatureType.SPIRIT, 155.0, 45.0, 9, 9, "Loses stamina quickly when startled", "witch.jpg");
        creature("Wild dog", CreatureType.BEAST, 80.0, 35.0, 10, 10, "Wants to play fetch", "dog.jpg");
        creature("Friendly bear", CreatureType.UNKNOWN, 170.0, 60.0, 1, 1, "Wants to snuggle", "bear.jpg");
        Area stockade = area("The Central Stockade",
                "The Central Stockade is a soul prison located in the middle of Newborn City. " +
                        "It contains a number of especially dangerous demons and undeads, amongst " +
                        "them the infamous Anubis himself.",
                0.0, 0.0, "stockade.jpg");
        area("Riverdown Valley",
                "Riverdown Valley is a one of the nicer zones covering the inhabitant's reactions to the " +
                        "Stonewrought Dam bursting during the Shattering and the resulting loss of water " +
                        "and environmental changes.",
                0.3, 1.31, "riverdown.jpg");
        area("Dun Morogh", "Dun Morogh is a snowy region located between the magma-strewn wasteland of the" + 
                " Searing Gorge to the south, the gentle ridges of Loch Modan to the east", 
                2.2, 2.3, "morogh.jpg");
        area("Rostok", "A desert oasis of sorts - a place completely devoid of anomalies and mutants. Be "+
                "sure to check out the 100 Rads bar in the vaults.", 
                3.0, 5.5, "rostok.jpg");
        area("Great Swamp", "The marshlands consist mostly of murky irradiated water and tall reeds, with thin passages of safe"+
                " land stretching like labyrinthine bridges all over.",
                5.2, 6.0, "swamp.jpg");
        area("Red Forest", "A dreadful place. Easy to vanish without a trace there, no matter how lucky you have been in life so far. ",
                6.0, 2.0, "forest.jpg");
        Weapon knife = weapon("Knife", 1.0, AmmunitionType.NONE, "knife.jpg");
        weaponService.assignCreature(knife, bongun);
        areaService.addCreatureToArea(bongun, stockade);
        weapon("Shotgun", 20.0, AmmunitionType.SHELL, "shotgun.jpg");
        weapon("Chainsaw", 3.0, AmmunitionType.OTHER, "chainsaw.jpg");
        weapon("Sixshooter", 30.0, AmmunitionType.BULLET, "sixshooter.jpg");
        weapon("Baseball bat", 3.0, AmmunitionType.NONE, "bat.jpg");
        weapon("Nuclear grenade", 8000.0, AmmunitionType.OTHER, "grenade.jpg");
        weapon("Tank", 200.0, AmmunitionType.BULLET, "tank.jpg");
        user("filip", "filip", UserRole.USER, "fuck", "user1.jpg");
        user("ondra", "a@b.c", UserRole.USER, "123", "user2.jpg");
        user("user", "user@user.cz", UserRole.USER, "user", "user3.jpg");
        user("admin", "admin@admin.cz", UserRole.ADMIN, "admin", "user4.jpg");
    }

    private Creature creature(String name, CreatureType type, Double height, Double weight, Integer agility, Integer ferocity, String weakness, String imageName) {
        Creature creature = new Creature();
        creature.setType(type);
        creature.setName(name);
        creature.setHeight(height);
        creature.setWeight(weight);
        creature.setAgility(agility);
        creature.setFerocity(ferocity);
        creature.setWeakness(weakness);
        try {
            creature.setImage(readImage(imageName));
        } catch (IOException e) {
            // IO problem
        }
        creature.setImageMimeType("image/jpeg");
        creatureService.createCreature(creature);
        return creature;
    }

    private Area area(String name, String description, Double latitude, Double longitude, String imageName) {
        Area area = new Area();
        area.setName(name);
        area.setDescription(description);
        area.setLatitude(latitude);
        area.setLongitude(longitude);
        try {
            area.setImage(readImage(imageName));
        } catch (IOException e) {
            // IO problem
        }
        area.setImageMimeType("image/jpeg");
        areaService.createArea(area);
        return area;
    }

    private Weapon weapon(String name, Double gunReach, AmmunitionType ammunition, String imageName) {
        Weapon weapon = new Weapon();
        weapon.setName(name);
        weapon.setGunReach(gunReach);
        weapon.setAmmunition(ammunition);
        try {
            weapon.setImage(readImage(imageName));
        } catch (IOException e) {
            // IO problem
        }
        weapon.setImageMimeType("image/jpeg");
        weaponService.addWeapon(weapon);
        return weapon;
    }

    private User user(String username, String email, UserRole role, String password, String imageName) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);
        try {
            user.setImage(readImage(imageName));
        } catch (IOException e) {
            // IO problem
        }
        user.setImageMimeType("image/jpeg");
        userService.registerUser(user, password);
        return user;
    }

    private byte[] readImage(String file) throws IOException {
        try (InputStream is = this.getClass().getResourceAsStream("/" + file)) {
            int nRead;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        }
    }
}
