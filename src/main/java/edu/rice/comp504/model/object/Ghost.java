package edu.rice.comp504.model.object;

import edu.rice.comp504.model.cmd.ICharacterCmd;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;
import java.beans.PropertyChangeEvent;

public class Ghost extends ACharacter{
    private boolean isFlashing;
    private boolean isDead;
    private int flashingTimer;
    private Point startLoc;

    /**
     * Constructor.
     *
     * @param name           The name of the ACharacter
     * @param loc            The location of the ACharacter on the canvas
     * @param vel            The ACharacter velocity
     * @param color          The ACharacter color
     * @param updateStrategy The object updateStrategy
     * @param direction      The character direction
     * @param size
     */
    public Ghost(String name, Point loc, int vel, String color, IUpdateStrategy updateStrategy, int direction, int size,
                 boolean isFlashing, boolean isDead, int flashingTimer) {
        super(name, loc, vel, color, updateStrategy, direction, size);
        this.isFlashing = isFlashing;
        this.isDead = isDead;
        this.flashingTimer = flashingTimer;
        this.startLoc = loc;
    }

    /**
     * See if Ghost is flashing.
     * @return Whether ghost is flashing.
     */
    public boolean isFlashing() {
        return isFlashing;
    }

    /**
     * Set if Ghost is flashing.
     * @param flashing If ghost is flashing.
     */
    public void setFlashing(boolean flashing) {
        this.isFlashing = flashing;
    }

    /**
     * Set the Ghost to its spawn location.
     */
    public void setStartLoc() {
        setLoc(this.startLoc);
    }

    /**
     * See if Ghost is dead.
     * @return if ghost is dead.
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Set if Ghost is dead.
     * @param dead if ghost is dead.
     */
    public void setDead(boolean dead) {
        this.isDead = dead;
    }

    /**
     * Get the remaining flashing time of the Ghost.
     * @return The Ghost flashing time.
     */
    public int getFlashingTimer() {
        return flashingTimer;
    }

    /**
     * Set the flashing time left of the Ghost.
     * @param flashingTimer The Ghost flashing time.
     */
    public void setFlashingTimer(int flashingTimer) {
        this.flashingTimer = flashingTimer;
    }

    /**
     * Execute the command
     */
    public void executeCommand(ICharacterCmd command, ACharacter pacman) {
        command.execute(pacman, this);
    }
}
