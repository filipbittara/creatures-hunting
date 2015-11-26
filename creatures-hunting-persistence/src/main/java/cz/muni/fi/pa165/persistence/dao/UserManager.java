/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.User;
import java.util.List;

/**
 *
 * @author Ondrej Klein
 */
public interface UserManager {
	public User findUser(Long id);
	public void addUser(User user);
	public void deleteUser(User user);
	public void updateUser(User user);	
	public List<User> findAllUsers();
        public User findUserByEmail(String email);
}
