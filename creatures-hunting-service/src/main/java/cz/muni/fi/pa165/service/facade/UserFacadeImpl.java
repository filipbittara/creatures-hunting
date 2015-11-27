package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.persistence.entity.User;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.UserService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Filip Bittara
 */

@Service
@Transactional
public class UserFacadeImpl implements UserFacade{
    
    @Autowired
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    public void setUserService(UserService creatureService) {
        this.userService = creatureService;
    }

    public void setBeanMappingService(BeanMappingService beanMappingService) {
        this.beanMappingService = beanMappingService;
    }

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userService.findUserById(userId);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public void registerUser(UserDTO user, String unencryptedPassword) {
        User userEntity = beanMappingService.mapTo(user, User.class);
        userService.registerUser(userEntity, unencryptedPassword);
        user.setId(userEntity.getId());
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.getAllUsers(), UserDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO auth) {
        return userService.authenticate(
                userService.findUserById(auth.getUserId()), auth.getPassword());
    }

    @Override
    public boolean isAdmin(UserDTO user) {
        return userService.isAdmin(beanMappingService.mapTo(user, User.class));
    }
    
    @Override
    public void changePassword(UserAuthenticateDTO auth, String newUnencryptedPassword) {
        userService.changePassword(userService.findUserById(auth.getUserId()), auth.getPassword(), newUnencryptedPassword);
    }

}
