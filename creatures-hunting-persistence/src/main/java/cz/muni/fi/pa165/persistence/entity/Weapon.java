package cz.muni.fi.pa165.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
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

    private String ammunition;

    @ManyToMany
    private List<Creature> creatures = new ArrayList<Creature>();

    public void addCreature(Creature creature) {
        getCreatures().add(creature);
    }

    public Long getId() {
        return id;
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

    public String getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(String ammunition) {
        this.ammunition = ammunition;
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(List<Creature> creatures) {
        this.creatures = creatures;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * 3 + ((id == null) ? 0 : id.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weapon weapon = (Weapon) o;

        return getId().equals(weapon.getId());

    }
}
