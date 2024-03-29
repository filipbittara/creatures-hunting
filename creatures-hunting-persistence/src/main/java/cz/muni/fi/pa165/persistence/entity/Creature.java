package cz.muni.fi.pa165.persistence.entity;


import cz.muni.fi.pa165.persistence.entity.enums.CreatureType;
import org.hibernate.validator.constraints.Range;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Basic entity class - Creature.
 * For reference model see project's github wiki (https://github.com/kizivat/creatures-hunting/wiki). 
 * @author Filip Bittara
 */
@Entity
public class Creature {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable=false,unique=true)
    private String name;
    
    private CreatureType type;

    @Min(value=0)
    private Double height;

    @Min(value=0)
    private Double weight;

    @Range(min=0, max=10)
    private Integer agility;

    @Range(min=0, max=10)
    private Integer ferocity;
    private String weakness;
    
    @ManyToMany(mappedBy="creatures")
    private Set<Weapon> weapons = new HashSet<Weapon>();
    
    @ManyToMany(mappedBy="creatures")
    private Set<Area> areas = new HashSet<Area>();

    @Lob
    private byte[] image;
    private String imageMimeType;
    
    /**
     * Method assigns weapon to creature.
     * Should NOT be used, use addCreature() method of Weapon class instead.
     * @param weapon weapon to be added 
     */
    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }
    
    /**
     * Method assigns area to creature.
     * Should NOT be used, use addCreature() method of Area class instead.
     * @param area area to be added 
     */
    public void addArea(Area area) {
        areas.add(area);
        if (!area.getCreatures().contains(this))
            area.addCreature(this);
    }
    
    public void removeArea(Area area) {
        if(areas.contains(area)) {
            areas.remove(area);
        } else {
            throw new IllegalArgumentException("Creature " + this.getName() + "is not in the area " + area.getName());
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getAgility() {
        return agility;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getFerocity() {
        return ferocity;
    }

    public void setFerocity(Integer ferocity) {
        this.ferocity = ferocity;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

    public Set<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(Set<Weapon> weapons) {
        this.weapons = weapons;
    }

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }
    
    public CreatureType getType() {
        return type;
    }

    public void setType(CreatureType type) {
        this.type = type;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Creature)) {
            return false;
        }
        Creature other = (Creature) obj;
	if (name == null) {
            if (other.getName() != null) {
		return false;
            }
        } else if (!name.equals(other.getName())) {
            return false;
        }
	return true;
    }
}
