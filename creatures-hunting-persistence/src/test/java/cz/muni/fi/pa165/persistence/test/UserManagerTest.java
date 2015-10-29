/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
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

    @Test(expectedExceptions=ConstraintViolationException.class)
    public void nullUserNameNotAllowed(){
            User u = new User();
            u.setUsername(null);
            userManager.addUser(u);		
    }

    @Test(expectedExceptions=PersistenceException.class)
    public void nameIsUnique(){
            User u = new User();
            u.setUsername("User 1");
            userManager.addUser(u);	
            u = new User();
            u.setUsername("User 1");
            userManager.addUser(u);
    }

    @Test()
    public void savesName(){
            User u = new User();
            u.setUsername("User 1");
            userManager.addUser(u);
            Assert.assertEquals(userManager.findUser(u.getId()).getUsername(), "User 1");
    }

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

