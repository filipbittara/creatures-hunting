package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.persistence.entity.Creature;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author Filip Bittara
 */
public class CreatureDTO {
    private Long id;
    private String name;  
    private CreatureType type;
    private Double height;
    private Double weight;
    private Integer agility;
    private Integer ferocity;
    private String weakness;
    private byte[] image;
    private String imageMimeType;
    private MultipartFile multipartImage;

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

    public CreatureType getType() { return type; }

    public void setType(CreatureType type) { this.type = type; }

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
    
    public MultipartFile getMultipartImage() {
        return multipartImage;
    }

    public void setMultipartImage(MultipartFile multipartImage) throws IOException {
        this.multipartImage = multipartImage;
        if (multipartImage.getSize() > 0) {
            this.image = multipartImage.getBytes();
        } else {
            this.image = null;
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
        if (!(obj instanceof CreatureDTO)) {
            return false;
        }
        final CreatureDTO other = (CreatureDTO) obj;
        if (!Objects.equals(this.name, other.getName())) {
            return false;
        }
        return true;
    }

}
