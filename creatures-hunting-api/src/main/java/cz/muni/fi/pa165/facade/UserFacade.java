package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import java.util.Collection;

/**
 * @author Filip Bittara
 */
public interface UserFacade {
    public UserDTO findUserById(Long userId);
    public UserDTO findUserByEmail(String email);
    public void registerUser(UserDTO user, String unencryptedPassword);
    public void changePassword(UserAuthenticateDTO auth, String newUnencryptedPassword);
    public Collection<UserDTO> getAllUsers();
    public boolean authenticate(UserAuthenticateDTO auth);
    public boolean isAdmin(UserDTO user);
}
