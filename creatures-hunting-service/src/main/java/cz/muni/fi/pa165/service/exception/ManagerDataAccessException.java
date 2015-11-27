/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.exception;

import org.springframework.dao.DataAccessException;

/**
 * @author Filip Bittara
 */
public class ManagerDataAccessException extends DataAccessException {

    public ManagerDataAccessException(String msg) {
        super(msg);
    }

    public ManagerDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
