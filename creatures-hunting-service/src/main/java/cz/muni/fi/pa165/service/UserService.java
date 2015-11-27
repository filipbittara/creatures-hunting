package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.User;
import java.util.Collection;

/**
 * @author Filip Bittara
 */
public interface UserService {
    /**
     * Retrieves user with given id.
     * @param userId id of user wanted
     * @return user with given id
     */
    User findUserById(Long userId);
    /**
     * Retrieves user with given email.
     * @param email email of user wanted
     * @return user with given email
     */
    User findUserByEmail(String email);
    /**
     * Creates new user. In process encrypts given password.
     * @param user user to be registered
     * @param unencryptedPassword password in unencrypted form
     */
    void registerUser(User user, String unencryptedPassword);
    /**
     * Changes user password. Requires user authentication. In process encrypts given password.
     * @param user user to be authenticated
     * @param password password od user 
     * @param newUnencryptedPassword new password in unencrypted form
     */
    void changePassword(User user, String password, String newUnencryptedPassword);
    /**
     * Return all users in system.
     * @return all users
     */
    Collection<User> getAllUsers();
    /**
     * Checks if for given name password matches password hash in database. 
     * @param user user to be authenticated
     * @param password password od user 
     * @return true if password matches hash, false else
     */
    boolean authenticate(User user, String password);
    /**
     * Checks if given user is admin.
     * @param user user to be checked
     * @return true if user is admin, false else
     */
    boolean isAdmin(User user);
}
