package cz.muni.fi.pa165.facade;
import cz.muni.fi.pa165.dto.CreatureDTO;
import java.util.List;

/**
 * @author Filip Bittara
 */
public interface CreatureFacade {
    public Long createCreature(CreatureDTO newCreature);
    public void updateCreature(CreatureDTO creature);
    public void deleteCreature(Long creatureId);
    public List<CreatureDTO> getAllCreatures();
    public List<CreatureDTO> getCreaturesInCircle(double latitude, double longitude, double radius);
    public List<CreatureDTO> getCreaturesByArea(String areaName);
    public List<CreatureDTO> getCreaturesByWeapon(String weaponName);
    public CreatureDTO getCreature(Long id);
}
