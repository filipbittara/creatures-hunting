package cz.muni.fi.pa165.persistence.test;

import cz.muni.fi.pa165.persistence.PersistenceApplicationContext;
import cz.muni.fi.pa165.persistence.dao.UserManager;
import cz.muni.fi.pa165.persistence.entity.User;
import cz.muni.fi.pa165.persistence.entity.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Test class to test area manager functionality.
 * @author Filip Bittara
 */
@ContextConfiguration(classes=PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserManagerTest extends AbstractTestNGSpringContextTests {
    @Autowired
    UserManager userManager;
    
    @PersistenceContext 
    private EntityManager em;

    @Test()
    public void findTest() {
        User u1 = new User();
        u1.setUsername("User 1");

        userManager.addUser(u1);

        User result = userManager.findUser(u1.getId());

        Assert.assertEquals(result, u1);
    }
    
    /**
     * Checks insert to DB as well as obtaining all elements from it.
     */
    @Test
    public void findAll(){
        User u1 = new User();
        User u2 = new User();
        u1.setUsername("User 1");
        u2.setUsername("User 2");

        userManager.addUser(u1);
        userManager.addUser(u2);

        List<User> users  = userManager.findAllUsers();

        Assert.assertEquals(users.size(), 2);
        Assert.assertTrue(users.contains(u1));
        Assert.assertTrue(users.contains(u2));

    }

     /**
     * Checks that entity with null name cannot be saved.
     */
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void nullUserNameNotAllowed(){
            User u = new User();
            u.setUsername(null);
            userManager.addUser(u);		
    }

    /**
     * Checks that entity name must be unique.
     */
    @Test(expectedExceptions=PersistenceException.class)
    public void nameIsUnique(){
            User u = new User();
            u.setUsername("User 1");
            userManager.addUser(u);	
            u = new User();
            u.setUsername("User 1");
            userManager.addUser(u);
    }

    /**
     * Checks that name is saved properly.
     */
    @Test()
    public void savesName(){
            User u = new User();
            u.setUsername("User 1");
            userManager.addUser(u);
            Assert.assertEquals(userManager.findUser(u.getId()).getUsername(), "User 1");
    }

    /**
     * Checks that entity could be removed.
     */
    @Test()
    public void delete(){
            User u = new User();
            u.setUsername("User 1");
            userManager.addUser(u);
            Assert.assertNotNull(userManager.findUser(u.getId()));
            userManager.deleteUser(u);
            Assert.assertNull(userManager.findUser(u.getId()));
    }
    
    /**
     * Tests deletion of null user
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNull() {
        userManager.deleteUser(null);
    }
    
    /**
     * Checks that entity could be updated.
     */
    @Test
    public void update(){
            User u = new User();
            u.setUsername("User 1");
            userManager.addUser(u);
            Assert.assertEquals(userManager.findUser(u.getId()).getUsername(), "User 1");
            u.setUsername("Other user");
            userManager.updateUser(u);
            Assert.assertEquals(userManager.findUser(u.getId()).getUsername(), "Other user");
    }
    
    /**
     * Checks that updating null entity throws an exception.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNull() {
        userManager.updateUser(null);
    }
    
    /**
     * Checks that updating name to null throws an exception.
     */
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateNullName() {
        User u = new User();
        u.setUsername("admin");
        userManager.addUser(u);
        u.setUsername(null);
        userManager.updateUser(u);
    }
    
    /**
     * Checks that updating name to existing one throws an exception.
     */
    @Test(expectedExceptions = javax.persistence.PersistenceException.class)
    public void updateToNonUniqueName() {
        User u = new User();
        u.setUsername("admin");
        userManager.addUser(u);
        
        User u2 = new User();
        u2.setUsername("user");
        userManager.addUser(u2);
        
        u2.setUsername(u.getUsername());
        userManager.updateUser(u2);
    }
    /**
     * Checks that finding user by email works.
     */
    @Test
    public void findUserByEmailTest() {
        User u = new User();
        u.setUsername("User 1");
        u.setEmail("user1@creatureshunting.com");

        userManager.addUser(u);

        User res = userManager.findUserByEmail("user1@creatureshunting.com");

        Assert.assertEquals(res.getId(), u.getId());
        Assert.assertEquals(res.getUsername(), u.getUsername());
        Assert.assertEquals(res.getEmail(), u.getEmail());
        Assert.assertEquals(res.getPassword(), u.getPassword());
        Assert.assertEquals(res.getRole(), u.getRole());
    }
    
    /**
     * Checks that finding user by email works.
     */
    @Test
    public void findUserByUsernameTest() {
        User u = new User();
        u.setUsername("admin");
        u.setEmail("admin@admin.cz");
        u.setRole(UserRole.ADMIN);

        userManager.addUser(u);

        User res = userManager.findUserByUsername("admin");

        Assert.assertEquals(res.getId(), u.getId());
        Assert.assertEquals(res.getUsername(), u.getUsername());
        Assert.assertEquals(res.getEmail(), u.getEmail());
        Assert.assertEquals(res.getPassword(), u.getPassword());
        Assert.assertEquals(res.getRole(), u.getRole());
    }
    
    /**
     * Checks that finding user by null email throws exception.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findUserNullEmail() {
        userManager.findUserByEmail(null);
    }
    
    /**
     * Checks that finding user by empty email throws exception.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findUserEmptyEmail() {
        userManager.findUserByEmail("");
    }
    
    /**
     * Checks that finding user by null username throws exception.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findUserNullUsername() {
        userManager.findUserByUsername(null);
    }
    
    /**
     * Checks that finding user by empty username throws exception.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findUserEmptyUsername() {
        userManager.findUserByUsername("");
    }
}

