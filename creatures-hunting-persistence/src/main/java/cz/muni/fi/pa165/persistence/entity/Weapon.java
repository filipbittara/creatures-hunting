package cz.muni.fi.pa165.persistence.entity;

import cz.muni.fi.pa165.persistence.entity.enums.AmmunitionType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a weapon.
 *
 * @author David Kizivat
 */

@Entity
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private Double gunReach;

    private AmmunitionType ammunition;

    @ManyToMany
    private Set<Creature> creatures = new HashSet<>();
    
    @Lob
    private byte[] image;
    
    private String imageMimeType;

    /**
     * Assigns a creature that the weapon is effective against.
     *
     * @param creature instance of creature to be assigned
     */
    public void addCreature(Creature creature) {
        getCreatures().add(creature);
    }
	
    public void removeCreature(Creature creature) {
        if (creatures.contains(creature)) {
                creatures.remove(creature);
        }
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getGunReach() {
        return gunReach;
    }

    public void setGunReach(Double gunReach) {
        this.gunReach = gunReach;
    }

    public AmmunitionType getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(AmmunitionType ammunition) {
        this.ammunition = ammunition;
    }

    public Set<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(Set<Creature> creatures) {
        this.creatures = creatures;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + ((name == null) ? 0 : name.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Weapon)) return false;

        Weapon weapon = (Weapon) o;

        return getName().equals(weapon.getName());

    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageMimeType() {
        return imageMimeType;
    }

    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }
}
