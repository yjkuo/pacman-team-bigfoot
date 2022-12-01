package edu.rice.comp504.adapter;

import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.cmd.ICharacterCmd;
import edu.rice.comp504.model.cmd.SwitchStrategyCmd;
import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.AItem;
import edu.rice.comp504.model.object.AObject;
import edu.rice.comp504.model.object.Ghost;

import java.util.List;

public class DispatchAdapter {
    private static GameStore store;

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
     * Call the update method on all the characters to update their position in the pacman world (based on input direction
     * from user).
     */
    public GameStore updateStore(int direction) {
        store.update(direction);
        return store;
    }
}
