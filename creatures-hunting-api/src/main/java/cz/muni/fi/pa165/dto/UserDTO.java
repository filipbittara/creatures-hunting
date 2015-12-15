package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.UserRole;
import cz.muni.fi.pa165.persistence.entity.User;

import java.util.Objects;

/**
 *
 * @author Filip Bittara
 */
public class UserDTO {
    private Long id;
    private UserRole role;
    private String username;
    private String password;
    private String email;
    private byte[] image;
    private String imageMimeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        hash = 79 * hash + ((username == null) ? 0 : username.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserDTO)) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        if (!Objects.equals(this.username, other.getUsername())) {
            return false;
        }
        return true;
    }
}
