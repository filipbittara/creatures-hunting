/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.entity;

import org.hibernate.validator.constraints.Range;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ondrej Klein
 */
@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Range(min=-180, max=180)
    private Double latitude;

    @Range(min=-180, max=180)
    private Double longitude;

    @ManyToMany
    private Set<Creature> creatures = new HashSet<Creature>();

    @Lob
    private byte[] image;
    private String imageMimeType;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(Set<Creature> creatures) {
        this.creatures = creatures;
    }

    public void addCreature(Creature c) {
        creatures.add(c);
        if (!c.getAreas().contains(this)) {
            c.addArea(this);
        }
    }

    public void removeCreature(Creature c) {
        if (creatures.contains(c)) {
            creatures.remove(c);
            c.removeArea(this);
        } else {
            throw new IllegalArgumentException("Creature " + c.getName() + " not in the area" + this.getName());
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + ((name == null) ? 0 : name.hashCode());
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
        if (!(obj instanceof Area)) {
            return false;
        }
        final Area other = (Area) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public String getImageMimeType() {
        return imageMimeType;
    }

    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
