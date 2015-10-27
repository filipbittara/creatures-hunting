package cz.muni.fi.pa165.persistence.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Filip Bittara
 */
public class Creature {
    private Long id;
    private String name;   
    private Double height;
    private Double weight;
    private Integer agility;
    private Integer ferocity;
    private String weakness;
    private List<Weapon> weapons = new ArrayList<Weapon>();
    private List<Area> areas = new ArrayList<Area>();
    
    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }
    
    public void addArea(Area area) {
        areas.add(area);
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

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
