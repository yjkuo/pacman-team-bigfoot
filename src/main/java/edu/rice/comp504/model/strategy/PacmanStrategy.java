package edu.rice.comp504.model.strategy;

import edu.rice.comp504.adapter.DispatchAdapter;
import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.AItem;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.Pacman;
import java.lang.Math;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PacmanStrategy implements IUpdateStrategy {
    private int[][] layout;

    public PacmanStrategy(int[][] layout) {
        this.layout = layout;
    }

    /**
     * Update the state of a character.
     * @param character  The character to apply the strategy to.
     * @param aCharacter The character to apply the strategy to.
     */
    public void updateState(ACharacter character, ACharacter aCharacter) {
        Pacman pacman = (Pacman) character;
        int nextDirection = pacman.getNextDirection();

        if (nextDirection != pacman.getDirection() && !pacman.detectCollisionWithWalls(nextDirection, layout)) {
            pacman.setDirection(nextDirection);
        }

        int direction = pacman.getDirection();
        if (!pacman.detectCollisionWithWalls(direction, layout)) {
            Point locAfterMoveInDirection = pacman.locationAfterMoveInDirection(direction);

            pacman.setLoc(locAfterMoveInDirection);
        }
    }


    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName() {
        return "Pacman";
    }
}
