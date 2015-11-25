package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.dto.CreatureDTO;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Ondrej Klein
 */
public class WeaponDTO {
	private Long id;
	private String name;
	private Double gunReach;
	private String ammunition;
	private Set<CreatureDTO> creatures = new HashSet<>();

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

	public String getAmmunition() {
		return ammunition;
	}

	public void setAmmunition(String ammunition) {
		this.ammunition = ammunition;
	}

	public Set<CreatureDTO> getCreatures() {
		return creatures;
	}

	public void setCreatures(Set<CreatureDTO> creatures) {
		this.creatures = creatures;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + ((name == null) ? 0 : name.hashCode());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof WeaponDTO)) {
			return false;
		}
		final WeaponDTO other = (WeaponDTO) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
	}
	
	
}
