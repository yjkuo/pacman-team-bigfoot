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
        store.setCurrentScore(store.getCurrentScore() + store.getGhostScore());
        store.setGhostScore(store.getGhostScore() * 2);
    }

    /**
     * Call the update method on all the characters to update their position in the pacman world (based on input direction
     * from user).
     */
    public GameStore updateStore(int direction) {
        store.update(direction);
        return store;
    }

    /**
     * Extra action for characters.
     */
    private void ghostsAction() {
        for (Ghost ghost : store.getGhosts()) {
            ICharacterCmd switchCmd = null;
            if (ghost.isFlashing() && ghost.getFlashingTimer() <= 0) {
                ghost.setFlashing(false);
                switchCmd = resetGhostStrategy(ghost, switchCmd);
                store.setGhostScore(200);
            } else if ((ghost.getLoc().equals(ghost.getOriginalLoc()) && ghost.isDead())) {
                switchCmd = resetGhostStrategy(ghost, switchCmd);
                ghost.setDead(false);
            } else if (ghost.isDead()) {
                if (!ghost.getUpdateStrategy().getName().equals("return to base")) {
                    switchCmd = new SwitchStrategyCmd("ReturnToBase");
                    this.eatGhost(ghost);
                }
            } else if (ghost.isFlashing()) {
                switchCmd = new SwitchStrategyCmd("RunAway");
            }
            if (switchCmd != null) {
                switchCmd.execute(ghost);
            }
        }
    }

    /**
     * Reset the ghost strategy based on ghost's color.
     * @param ghost The ghost object.
     * @param switchCmd Switch Command.
     * @return The Switch Command.
     */
    private ICharacterCmd resetGhostStrategy(Ghost ghost, ICharacterCmd switchCmd) {
        switch (ghost.getColor()) {
            case "red":
                switchCmd = new SwitchStrategyCmd("Chase");
                break;
            case "blue":
                switchCmd = new SwitchStrategyCmd("Chase");
                break;
            case "orange":
                switchCmd = new SwitchStrategyCmd("Walk");
                break;
            case "pink":
                switchCmd = new SwitchStrategyCmd("Walk");
                break;
            default:
                break;
        }
        return switchCmd;
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
    public static GameStore getStore() {
        //TODO
        return store;
    }
}
