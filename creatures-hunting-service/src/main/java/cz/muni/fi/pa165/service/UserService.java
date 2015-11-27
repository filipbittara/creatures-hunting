package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.User;
import java.util.Collection;

/**
 * @author Filip Bittara
 */
public interface UserService {
    User findUserById(Long userId);
    User findUserByEmail(String email);
    void registerUser(User user, String unencryptedPassword);
    void changePassword(User user, String password, String newUnencryptedPassword);
    Collection<User> getAllUsers();
    boolean authenticate(User user, String password);
    boolean isAdmin(User user);
}
