package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.object.ACharacter;

/**
 * The interface for update strategy of pacman. The pacman will use this update strategy to
 * update its location in the game.
 */
public interface IUpdatePacmanStrategy extends IUpdateStrategy{
    /**
     * Update the state of Pacman.
     *
     * @param pacman     The pacman to apply the strategy to.
     * @param aCharacter The character to apply the strategy to.
     */
    public void updateState(ACharacter pacman, ACharacter aCharacter);


    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName();
}
