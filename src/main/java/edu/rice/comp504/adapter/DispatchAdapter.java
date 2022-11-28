package edu.rice.comp504.adapter;

import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.AItem;
import edu.rice.comp504.model.object.AObject;

import java.util.List;

public class DispatchAdapter {
    private GameStore store;

    /**
     * Constructor.
     */
    public DispatchAdapter() {
        store = new GameStore();
    }

    /**
     * Initialize the game.
     * @return GameStore so that information can be displayed
     */
    public GameStore initializeGame(int gameLevel, int numberOfGhosts, int lives) {
        store.init(gameLevel, numberOfGhosts, lives);
        return store;
    }

    /**
     * Pacman eat a dot.
     * @param dot The eaten dot.
     */
    public void eatDot(AItem dot) {
        //TODO
    }

    /**
     * Pacman eat a big dot.
     * @param bigDot The eaten big dot.
     */
    public void eatBigDot(AItem bigDot) {
        //TODO
    }

    /**
     * Pacman eat a fruit.
     * @param fruit The eaten fruit.
     */
    public void eatFruit(AItem fruit) {
        //TODO
    }

    /**
     * Pacman eat a ghost.
     * @param ghost The eaten ghost.
     */
    public void eatGhost(ACharacter ghost) {
        //TODO
    }

    /**
     * Call the update method on all the characters to update their position in the pacman world (based on input direction
     * from user).
     */
    public GameStore updateStore(String direction) {
        store.updateStore(direction);
        return store;
    }

    /**
     * Remove all PropertyChangeListener.
     */
    public void removeAll() {
        //TODO
    }

    /**
     * Set game settings.
     * @param gameLevel The game level.
     * @param numGhosts Number of ghosts.
     * @param numLives Number of lives.
     */
    public void setGameParameters(int gameLevel, int numGhosts, int numLives) {
        //TODO
    }

    /**
     * Get game store.
     * @return The game store.
     */
    public GameStore getStore() {
        //TODO
        return null;
    }
}
