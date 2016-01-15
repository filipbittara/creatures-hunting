package cz.muni.fi.pa165.dto;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Filip Bittara
 */
public class AreaDTO {

    private Long id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
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
        
        public void validateImage() {
            if (!multipartImage.getContentType().equals("image/jpeg")) {
                throw new RuntimeException("Only JPG images are accepted");
            }
        }
}
