package edu.rice.comp504.model.strategy.ghost;

import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

/**
 * The interface for update strategy of ghosts. The ghosts will use corresponding update strategies (chase or random walk) to
 * update its behaviors in the game.
 */
public interface IUpdateGhostStrategy extends IUpdateStrategy {
    /**
     * Update the state the Ghost.
     * @param pacman The pacman character to compare to.
     * @param ghost The ghost to apply the strategy to.
     */
    public void updateState(ACharacter pacman, ACharacter ghost);

    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName();
}
