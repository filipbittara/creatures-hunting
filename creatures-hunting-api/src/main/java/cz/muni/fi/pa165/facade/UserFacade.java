package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ChangeImageDTO;
import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import java.util.Collection;

/**
 * @author Filip Bittara
 */
public interface UserFacade {
    /**
     * Retrieves user with given id.
     * @param userId id of user wanted
     * @return user with given id
     */
    public UserDTO findUserById(Long userId);
    
     /**
     * Retrieves user with given email.
     * @param email email of user wanted
     * @return user with given email
     */
    public UserDTO findUserByEmail(String email);
    
    /**
     * Retrieves user with given username.
     * @param username username of user wanted
     * @return user with given username
     */
    public UserDTO findUserByUsername(String username);
    
    /**
     * Creates new user. In process encrypts given password.
     * @param user user to be registered
     * @param unencryptedPassword password in unencrypted form
     */
    public void registerUser(UserDTO user, String unencryptedPassword);
    
    /**
     * Changes user password. Requires user authentication. In process encrypts given password.
     * @param auth user authentication
     * @param newUnencryptedPassword new password in unencrypted form
     */
    public void changePassword(UserAuthenticateDTO auth, String newUnencryptedPassword);
    
    /**
     * Return all users in system.
     * @return all users
     */
    public Collection<UserDTO> getAllUsers();
    
    /**
     * Checks if for given name password matches password hash in database. 
     * @param auth user name/password combo
     * @return true if password matches hash, false else
     */
    public boolean authenticate(UserAuthenticateDTO auth);
    
    /**
     * Checks if given user is admin.
     * @param user user to be checked
     * @return true if user is admin, false else
     */
    public boolean isAdmin(UserDTO user);

    /**
     * Changes the user's image.
     *
     * @param imageChange new user's image
     */
    public void changeImage(ChangeImageDTO imageChange);
}
