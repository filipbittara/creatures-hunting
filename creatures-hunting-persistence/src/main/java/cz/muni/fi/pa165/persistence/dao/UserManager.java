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
	User findUser(Long id);
	Long addUser(User user);
	void deleteUser(User user);
	void updateUser(User user);
	List<User> findAllUsers();
	User findUserByEmail(String email);
}
