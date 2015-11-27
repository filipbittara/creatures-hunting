package cz.muni.fi.pa165.service.test;

import cz.muni.fi.pa165.persistence.dao.UserManager;
import cz.muni.fi.pa165.persistence.entity.User;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.service.configuration.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.testng.annotations.BeforeClass;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author David Kizivat
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private UserManager userManager;

    @Autowired
    @InjectMocks
    private UserService userService;

    @BeforeClass
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private User user1;
    private User user2;

    private List<User> users;

    @BeforeMethod
    public void prepareUsers() {
        user1 = new User();
        user1.setUsername("User1");
        user1.setEmail("user1@creatureshunting.com");
        user1.setPassword("password1");
        user1.setRole(1);

        user2 = new User();
        user2.setUsername("User2");
        user2.setEmail("user2@creatureshunting.com");
        user2.setPassword("password2");
        user2.setRole(2);

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    /**
     * Checks if findUser() method of weapon service uses user manager correctly.
     */
    @Test
    public void findUserByIdTest() {
        userService.registerUser(user1, user1.getPassword());
        userService.findUserById(1L);
        verify(userManager, times(1)).findUser(1L);
    }

    @Test
    public void findUserByEmailTest() {
        userService.registerUser(user1, user1.getPassword());
        User res = userService.findUserByEmail(user1.getEmail());
        verify(userManager, times(1)).findUserByEmail(user1.getEmail());

        when(userManager.findUserByEmail(user1.getEmail())).thenReturn(user1);
    }

    @Test
    public void registerUserTest() {
        userService.registerUser(user1, user1.getPassword());
        verify(userManager, atLeastOnce()).addUser(user1);
    }

    @Test
    public void changePasswordTest() {
        userService.registerUser(user1, user1.getPassword());
        userService.changePassword(user1, "password1", "password3");

        verify(userManager, times(1)).updateUser(user1);
    }

    @Test
    public void getAllUsersTest() {
        userService.registerUser(user1, user1.getPassword());
        verify(userManager, atLeastOnce()).addUser(user1);

        userService.registerUser(user2, user2.getPassword());
        verify(userManager, atLeastOnce()).addUser(user2);

        when(userManager.findAllUsers()).thenReturn(users);

        org.junit.Assert.assertEquals(userManager.findAllUsers(), users);
    }

    @Test
    public void authenticateTest() {
        userService.registerUser(user1, user1.getPassword());
        userService.registerUser(user2, user2.getPassword());
        boolean res1 = userService.authenticate(user1, "password1");
        boolean res2 = userService.authenticate(user1, "password2");

        Assert.assertEquals(res1, true);
        Assert.assertEquals(res2, false);
    }

    @Test
    public void isAdminTest() {
        when(userManager.addUser(user1)).thenReturn(1L);
        when(userManager.addUser(user2)).thenReturn(2L);
        userService.registerUser(user1, user1.getPassword());
        userService.registerUser(user2, user2.getPassword());
        
        when(userManager.findUser(user1.getId())).thenReturn(user1);
        when(userManager.findUser(user2.getId())).thenReturn(user2);
        boolean res1 = userService.isAdmin(user1);
        boolean res2 = userService.isAdmin(user2);

        when(userManager.findUser(user1.getId())).thenReturn(user1);
        when(userManager.findUser(user2.getId())).thenReturn(user2);

        Assert.assertEquals(res1, true);
        Assert.assertEquals(res2, false);
    }
}
