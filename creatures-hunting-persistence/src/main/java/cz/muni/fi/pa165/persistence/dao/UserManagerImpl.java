/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
	public Long addUser(User user) {
		em.persist(user);
                return user.getId();
	}

	@Override
	public void deleteUser(User user) {
		em.remove(user);
	}

	@Override
	public void updateUser(User user) {
		em.merge(user);
	}

	@Override
	public List<User> findAllUsers() {
		return em.createQuery("select u from User u", User.class)
				.getResultList();
	}
        
        @Override
	public User findUserByEmail(String email) {
		if (email == null || email.isEmpty())
			throw new IllegalArgumentException("Cannot search for null e-mail");
		try {
                    return em.createQuery("select u from User u where email=:email", User.class).setParameter("email", email).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	
}
