/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ondra
 */
@Repository
public class UserManagerImpl implements UserManager {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public User findUser(Long id) {
		return em.find(User.class, id);
	}

	@Override
	public void addUser(User user) {
		em.persist(user);
	}

	@Override
	public void deleteUser(User user) {
		em.remove(user);
	}

	@Override
	public void updateUser(User user) {
		em.merge(user);
	}
}
