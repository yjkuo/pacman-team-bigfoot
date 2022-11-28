package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.object.ACharacter;

public interface IUpdateStrategy {
    /**
     * Update the state of a character.
     * @param character The character to apply the strategy to.
     */
    public void updateState(ACharacter character);


    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName();
}
