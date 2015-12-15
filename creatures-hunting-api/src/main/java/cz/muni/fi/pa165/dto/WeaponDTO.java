package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.enums.AmmunitionType;
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
	private AmmunitionType ammunition;
	private Set<CreatureDTO> creatures = new HashSet<>();
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

	public Set<CreatureDTO> getCreatures() {
		return creatures;
	}

	public void addCreature(CreatureDTO creature) {
		this.creatures.add(creature);
	}

	public void removeCreature(CreatureDTO creature) {
		if (creature != null && this.creatures.contains(creature)) {
			this.creatures.remove(creature);
		}
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
		if (!Objects.equals(this.name, other.getName())) {
			return false;
		}
		return true;
	}
}
