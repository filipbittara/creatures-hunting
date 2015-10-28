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
	private EntityManagerFactory emf;

	@Autowired
	public WeaponManager wm;

	@Autowired
	public CreatureManager cm;
	
	private Weapon w1;
	private Weapon w2;
	
	@BeforeTransaction
	public void beforeTransaction() {
		new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);
		emf = Persistence.createEntityManagerFactory("default");
	}

	@AfterTransaction
	public void afterTransaction() {
		emf.close();
	}
	
	@BeforeMethod
	public void createProducts() {
		w1 = new Weapon();
		w2 = new Weapon();

		w1.setName("shotgun1");
		w2.setName("machete2");
		
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
	
	
	
}
