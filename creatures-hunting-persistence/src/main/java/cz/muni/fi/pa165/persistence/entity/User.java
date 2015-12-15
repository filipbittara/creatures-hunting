/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.entity;

import cz.muni.fi.pa165.persistence.entity.enums.UserRole;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ondrej Klein
 */
@Entity
@Table(name="SYSTEM_USERS")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private UserRole role;

    @NotNull
    @Column(nullable=false, unique=true)
    private String username;

    private String password;

    private String email;

    @Lob
    private byte[] image;
    private String imageMimeType;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
	
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
            int hash = 7;
            hash = 71 * hash + ((username == null) ? 0 : username.hashCode());
            return hash;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        if (other.username == null) {
            if (this.username != null) {
                return false;
            }   
        } else if (this.username != other.username) {
            return false;
        }
        	
        return true;

    }
}
