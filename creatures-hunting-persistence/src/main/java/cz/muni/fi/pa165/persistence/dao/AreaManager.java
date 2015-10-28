/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.Area;
import java.util.List;

/**
 *
 * @author Ondrej Klein
 */
public interface AreaManager {
	public Area findArea(Long id);
	public void addArea(Area a);
	public void deleteArea(Area a);
	public void updateArea(Area a);
	public List<Area> findAllAreas();  
	public void addCreature();
}
