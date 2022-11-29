package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.object.ACharacter;

public class PacmanStrategy implements IUpdateStrategy {
    private int[][] layout;

    public PacmanStrategy(int[][] layout) {
        this.layout = layout;
    }
    /**
     * Update the state of a character.
     *
     * @param character  The character to apply the strategy to.
     * @param aCharacter
     */
    public void updateState(ACharacter character, ACharacter aCharacter) {

    }


    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName() {
        return "Pacman";
    }
}
