/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;

/**
 *
 * @author Filip Bittara
 */
public interface CreatureManager {
    public Creature findCreature(Long id);
    public void addCreature(Creature creature);
    public void deleteCreature(Creature creature);
    public void updateCreature(Creature creature);
    public List<Creature> findAllCreatures();
}
