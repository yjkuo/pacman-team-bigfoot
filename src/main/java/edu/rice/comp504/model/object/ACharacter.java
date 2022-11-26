package edu.rice.comp504.model.object;

import edu.rice.comp504.model.strategy.IUpdatePacmanStrategy;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents the moving characters in the game (ghost and pacman).
 */
public class ACharacter extends AObject implements PropertyChangeListener {
    private Point vel;
    private IUpdatePacmanStrategy updateStrategy;
    // 1 is left, 2 is up, 3 is right, and 4 is down
    private int direction;
    private final Point originalLoc;
    private int size;

    /**
     * Constructor.
     * @param name The name of the ACharacter
     * @param loc  The location of the ACharacter on the canvas
     * @param vel  The ACharacter velocity
     * @param color The ACharacter color
     * @param updateStrategy  The object updateStrategy
     * @param direction The character direction
     */
    public ACharacter(String name, Point loc, Point vel, String color, IUpdatePacmanStrategy updateStrategy,
                      int direction, int size) {
        super(name, loc, color);
        this.vel = vel;
        this.updateStrategy = updateStrategy;
        this.direction = direction;
        this.originalLoc = new Point(loc.x, loc.y);
        this.size = size;
    }

    /**
     * Get the ACharacter updateStrategy.
     * @return The ACharacter updateStrategy.
     */
    public IUpdatePacmanStrategy getUpdateStrategy() {
        return this.updateStrategy;
    }

    /**
     * Set the updateStrategy of the ACharacter.
     * @param updateStrategy  The new updateStrategy
     */
    public void setUpdateStrategy(IUpdatePacmanStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    /**
     * Get the velocity of the ACharacter.
     * @return The ACharacter velocity
     */
    public Point getVel() {
        return this.vel;
    }

    /**
     * Set the velocity of the ACharacter.
     * @param vel The new ACharacter velocity
     */
    public void setVel(Point vel) {
        this.vel = vel;
    }

    /**
     * Get the ACharacter direction.
     * @return The ACharacter direction.
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Set the direction of the ACharacter.
     * @param direction  The new direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Get the ACharacter original location.
     * @return The ACharacter original location.
     */
    public Point getOriginalLoc() {
        return originalLoc;
    }


    /**
     * Get the size of the ACharacter.
     * @return The ACharacter size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the size of the ACharacter.
     * @param size The ACharacter size.
     */
    public void setSize(int size) {
        size = size;
    }

    /**
     * Detects collision between a ACharacter and a wall in the ACharacter world.  Change direction if ACharacter collides with a wall.
     * @return if it collides with a wall within a step.
     */
    public boolean detectCollisionWithWalls(int direction, int[][] layout) {
        //TODO wall collision detection
        return false;
    }

    /**
     * Character responds to property change event.
     * @param evt changed event.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        //TODO implement property change
    }
}
