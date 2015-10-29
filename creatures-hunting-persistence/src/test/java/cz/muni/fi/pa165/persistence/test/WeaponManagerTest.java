/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.test;

import cz.muni.fi.pa165.persistence.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.persistence.PersistenceApplicationContext;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Ondrej Klein
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class WeaponManagerTest extends AbstractTestNGSpringContextTests {
	
	@PersistenceContext
	public EntityManager em;


	@Autowired
	public WeaponManager wm;

	@Autowired
	public CreatureManager cm;
	
	private Weapon w1;
	private Weapon w2;
	
	private String wName1;
	private String wName2;
	

	
	@BeforeMethod
	public void createWeapons() {
		wName1 = "shotgun1";
		wName2 = "machete2";
		
		w1 = new Weapon();
		w2 = new Weapon();

		w1.setName(wName1);
		w2.setName(wName2);
		
		w1.setGunReach(Double.valueOf(25.3));
		w2.setGunReach(Double.valueOf(0.9));
		
		w1.setAmmunition("shells");
		w2.setAmmunition("musclepower");

		wm.addWeapon(w1);
		wm.addWeapon(w2);
	}
	
	@Test
	public void findAll() {
		List<Weapon> weapons = wm.findAllWeapons();
		Assert.assertEquals(weapons.size(), 2);
		Assert.assertTrue(weapons.contains(w1));
		Assert.assertTrue(weapons.contains(w2));
		
	}
	
	@Test
	public void addWeapon() {
		Weapon w3 = new Weapon();
		w3.setName("test");
		w3.setAmmunition("test");
		w3.setGunReach(Double.MAX_VALUE);
		wm.addWeapon(w3);
		Weapon testWeapon = wm.findWeapon(w3.getId());
		Assert.assertEquals(testWeapon, w3);
	}
	
	@Test(expectedExceptions=ConstraintViolationException.class)
    public void nullAreaNameNotAllowed(){
            Weapon w3 = new Weapon();
            w3.setName(null);
            wm.addWeapon(w3);		
    }
	
	@Test(expectedExceptions = PersistenceException.class)
	public void notUniqueName() {
		Weapon w3 = new Weapon();
		w3.setName(wName1);
		wm.addWeapon(w3);
	}
	
	@Test
	public void deleteWeapon() {
		Assert.assertNotNull(wm.findWeapon(w1.getId()));
		wm.deleteWeapon(wm.findWeapon(w1.getId()));
		Assert.assertNull(wm.findWeapon(w1.getId()));
	}
	
	@Test
	public void updateWeapon() {
		Assert.assertEquals(wm.findWeapon(w1.getId()).getName(), wName1);
		String newName = "newName";
		w1.setName(newName);
		wm.updateWeapon(w1);
		Assert.assertEquals(wm.findWeapon(w1.getId()).getName(), newName);
	}
	
	
	
	
	
	
	
}
