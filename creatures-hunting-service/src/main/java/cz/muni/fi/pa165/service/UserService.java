package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.User;
import java.util.Collection;

/**
 * @author Filip Bittara
 */
public interface UserService {
    public User findUserById(Long userId);
    public User findUserByEmail(String email);
    public void registerUser(User user, String unencryptedPassword);
    public void changePassword(User user, String password, String newUnencryptedPassword);
    public Collection<User> getAllUsers();
    public boolean authenticate(User user, String password);
    public boolean isAdmin(User user);
}
