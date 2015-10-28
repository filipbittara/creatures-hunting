/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.User;

/**
 *
 * @author Ondrej Klein
 */
public interface UserManager {
	public User findUser(Long id);
	public void addUser(User user);
	public void deleteUser(User user);
	public void updateUser(User user);	
}
