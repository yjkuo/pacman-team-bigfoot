package edu.rice.comp504.model.object;

import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;

public class Pacman extends ACharacter{
    private int leftLives;
    private int nextDirection;
    private int deadState;
    private int deadSpeed = 0;
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
    public Pacman(String name, Point loc, int vel, String color, IUpdateStrategy updateStrategy, int direction, int size) {
        super(name, loc, vel, color, updateStrategy, direction, size);
    }

    /**
     * Set the pacman as dead or update its dead state.
     */
    public void setDeadState() {
        if (deadState == -1) {
            deadState = 0;
            reduceLive();
        } else if (deadState <= 12) {
            deadState++;
        } else {
            deadState = -1;
        }
    }

    /**
     * Get the dead state of pacman.
     * @return the dead state of pacman.
     */
    public int getDeadState() {
        return deadState;
    }

    /**
     * Reduce the pacman live by 1.
     */
    public void reduceLive() {
        this.leftLives--;
    }
}
