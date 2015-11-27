package cz.muni.fi.pa165.service.facade.test;

import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.persistence.entity.User;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.service.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.UserFacadeImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

/**
 * @author David Kizivat
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class UserFacadeTest {

    @Mock
    private UserService userService;

    private UserFacadeImpl userFacade;

    @Autowired
    private BeanMappingService beanMappingService;

    @org.testng.annotations.BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        userFacade = new UserFacadeImpl();
        userFacade.setUserService(userService);
        userFacade.setBeanMappingService(beanMappingService);

    }

    private UserDTO user1;
    private UserDTO user2;
    private UserDTO user3;
    private List<UserDTO> users;

    @BeforeMethod
    public void prepareUsers(){
        user1 = new UserDTO();
        user1.setUsername("User1");
        user1.setEmail("user1@creatureshunting.com");
        user1.setPassword("password1");
        user1.setRole(1);

        user1 = new UserDTO();
        user1.setUsername("User2");
        user1.setEmail("user2@creatureshunting.com");
        user1.setPassword("password2");
        user1.setRole(2);

        users = new ArrayList<UserDTO>();
        users.add(user1);
        users.add(user2);
    }

    /**
     * Checks if findUser() method of user service uses user manager correctly.
     */
    @Test
    public void findUserByIdTest() {
        when(userService.findUserById(1l)).thenReturn(beanMappingService.mapTo(user1, User.class));
        userService.findUserById(1L);
        verify(userFacade, times(1)).findUserById(1L);
    }

    @Test
    public void findUserByEmailTest() {
        when(userService.findUserByEmail("user1@creatureshunting.com")).thenReturn(beanMappingService.mapTo(user2, User.class));
        userFacade.findUserByEmail("user1@creatureshunting.com");
        verify(userService, times(1)).findUserByEmail("user1@creatureshunting.com");
    }

    @Test
    public void registerUserTest() {
        User result = beanMappingService.mapTo(user1, User.class);
        result.setId(1l);
        userFacade.registerUser(user1, user1.getPassword());
        verify(userService, times(1)).registerUser(beanMappingService.mapTo(user1, User.class), "password1");
    }

    @Test
    public void changePasswordTest() {
        throw new NotImplementedException();
    }

    @Test
    public void getAllUsersTest() {
        when(userService.getAllUsers()).thenReturn(beanMappingService.mapTo(users, User.class));
        userFacade.getAllUsers();
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void authenticateTest() {
        throw new NotImplementedException();
    }

    @Test
    public void isAdminTest() {
        throw new NotImplementedException();
    }

}
