package edu.rice.comp504.model.object;

import edu.rice.comp504.model.cmd.ICharacterCmd;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;

public class Pacman extends ACharacter{
    private int leftLives;
    private int nextDirection;

    /**
     * Constructor.
     *
     * @param name           The name of the ACharacter
     * @param loc            The location of the ACharacter on the canvas
     * @param vel            The ACharacter velocity
     * @param color          The ACharacter color
     * @param updateStrategy The object updateStrategy
     * @param direction      The character direction
     * @param size           The character size
     */
    public Pacman(String name, Point loc, int vel, String color, IUpdateStrategy updateStrategy, int direction, int size) {
        super(name, loc, vel, color, updateStrategy, direction, size);
        nextDirection = direction;
    }

    /**
     * Reduce the pacman live by 1.
     */
    public void reduceLive() {
        this.leftLives--;
    }


    /**
     * Get the next direction.
     * @return The next direction.
     */
    public int getNextDirection() {
        return nextDirection;
    }

    /**
     * Set the next direction.
     * @param direction  The new next direction.
     */
    public void setNextDirection(int direction) {
        this.nextDirection = direction;
    }

    /**
     * Check if pacman collide with ghost.
     * @param obj The object to check.
     */
    public boolean detectCollisionObj(AObject obj) {
        Point ghostLoc = obj.getLoc();
        int ghostSize = obj.getSize();
        Point loc = this.getLoc();
        int size = this.getSize();
        return Math.sqrt(Math.pow(loc.x - ghostLoc.x, 2) + Math.pow(loc.y - ghostLoc.y, 2)) <= (size / 2 + ghostSize / 2) - 2;
    }

    public void executeCommand(ICharacterCmd command) {
        command.execute(this);
    }

    /**
     * Reset the pacman.
     */
    public void reset() {
        this.setLoc(new Point(this.getOriginalLoc().x, this.getOriginalLoc().y));
    }
}
