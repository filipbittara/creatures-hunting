package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.User;
import java.util.List;

/**
 * Users data access object. For reference model see project's github wiki
 * (https://github.com/kizivat/creatures-hunting/wiki).
 *
 * @author Ondrej Klein
 */
public interface UserManager {

    /**
     * Finds and returns user with selected id.
     *
     * @param id id of wanted user
     * @return user with given id
     */
    User findUser(Long id);

    /**
     * Saves given user.
     *
     * @param user user to be saved
     */
    Long addUser(User user);

    /**
     * Removes given user.
     *
     * @param user user to be removed
     */
    void deleteUser(User user);

    /**
     * Updates given user, only if already exists.
     *
     * @param user user to be updated
     */
    void updateUser(User user);

    /**
     * Returns list of all users.
     *
     * @return list of all users
     */
    List<User> findAllUsers();

    /**
     * Searches for user with given email.
     *
     * @param email email of the user to be searched for
     * @return instance of the user with the given email
     */
    User findUserByEmail(String email);

    /**
     * Searches for user with given name.
     *
     * @param username username of the user to be searched for
     * @return instance of the user with the given username
     */
    User findUserByUsername(String username);
}
