package cz.muni.fi.pa165.persistence.test;

import cz.muni.fi.pa165.persistence.PersistenceApplicationContext;
import cz.muni.fi.pa165.persistence.dao.UserManager;
import cz.muni.fi.pa165.persistence.entity.User;
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
            User a = new User();
            a.setUsername("User 1");
            userManager.addUser(a);
            Assert.assertNotNull(userManager.findUser(a.getId()));
            userManager.deleteUser(a);
            Assert.assertNull(userManager.findUser(a.getId()));
    }
}

