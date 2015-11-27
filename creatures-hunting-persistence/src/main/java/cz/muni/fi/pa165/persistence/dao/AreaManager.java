package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.Area;

import java.util.List;

/**
 * Areas data access object.
 *
 * @author Ondrej Klein
 */
public interface AreaManager {
	/**
	 * Find an area by its ID in Areas table.
	 *
	 * @param id ID of the weapon that is being searched for
	 * @return weapon with she sufficient ID
	 */
	public Area findArea(Long id);

	/**
	 * Adds an area record.
	 *
	 * @param area instance of the area to be added.
	 */
	public void addArea(Area area);

    /**
     * Removes an area record.
     *
     * @param area instance of the area to be removed
     */
	public void deleteArea(Area area);

    /**
     * Updates chosen areas's column's values.
     *
     * @param area instance of the area to be updated featuring new data values
     */
	public void updateArea(Area area);

    /**
     * Searches for all area records.
     *
     * @return a set of all areas found
     */
	public List<Area> findAllAreas();

	/**
	 * Searches for area with given name.
	 *
	 * @param name name of the area to be searched for
	 * @return instance of the area with the given name
	 */
	public Area findAreaByName(String name);

}
