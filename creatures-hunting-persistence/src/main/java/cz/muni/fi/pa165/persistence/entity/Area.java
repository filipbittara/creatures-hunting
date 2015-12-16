/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ondrej Klein
 */

@Entity
public class Area {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable=false, unique=true)
    private String name;
    
    private String description;
    
    private double latitude;
    private double longitude;
    
    @OneToMany(mappedBy="area")
    private List<Creature> creatures = new ArrayList<>();
    
    public void addCreature(Creature creature) {
        this.creatures.add(creature);
    }

    public List<Creature> getCreatures() {
        return creatures;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
	
    

}



