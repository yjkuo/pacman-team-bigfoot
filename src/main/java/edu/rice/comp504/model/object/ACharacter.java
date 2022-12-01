package edu.rice.comp504.model.object;

import edu.rice.comp504.model.strategy.IUpdatePacmanStrategy;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents the moving characters in the game (ghost and pacman).
 */
public class ACharacter extends AObject implements PropertyChangeListener {
    private int vel;
    private IUpdateStrategy updateStrategy;
    // 0 is left, 1 is up, 2 is right, and 3 is down
    private int direction;
    private final Point originalLoc;

    /**
     * Constructor.
     * @param name The name of the ACharacter
     * @param loc  The location of the ACharacter on the canvas
     * @param vel  The ACharacter velocity
     * @param color The ACharacter color
     * @param updateStrategy  The object updateStrategy
     * @param direction The character direction
     */
    public ACharacter(String name, Point loc, int vel, String color, IUpdateStrategy updateStrategy,
                      int direction, int size) {
        super(name, loc, color, size);
        this.vel = vel;
        this.updateStrategy = updateStrategy;
        this.direction = direction;
        this.originalLoc = new Point(loc.x, loc.y);
    }

    /**
     * Get the ACharacter updateStrategy.
     * @return The ACharacter updateStrategy.
     */
    public IUpdateStrategy getUpdateStrategy() {
        return this.updateStrategy;
    }

    /**
     * Set the updateStrategy of the ACharacter.
     * @param updateStrategy  The new updateStrategy
     */
    public void setUpdateStrategy(IUpdateStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    /**
     * Get the velocity of the ACharacter.
     * @return The ACharacter velocity
     */
    public int getVel() {
        return this.vel;
    }

    /**
     * Set the velocity of the ACharacter.
     * @param vel The new ACharacter velocity
     */
    public void setVel(int vel) {
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
     * Move pacman to location given a direction.
     */
    public Point locationAfterMoveInDirection(int direction) {
        Point loc = new Point((int) this.getLoc().getX(), (int) this.getLoc().getY());
        int vel = this.getVel();
        switch (direction) {
            case 0: {
                loc.x -= vel;
                break;
            }
            case 1: {
                loc.y -= vel;
                break;
            }
            case 2: {
                loc.x += vel;
                break;
            }
            case 3: {
                loc.y += vel;
                break;
            }
            default:
        }
        return loc;
    }

    /**
     * Detects collision between a ACharacter and a wall in the ACharacter world.  Change direction if ACharacter collides with a wall.
     * @return if it collides with a wall within a step.
     */
    public boolean detectCollisionWithWalls(int direction, int[][] layout) {
        Point locAfterMoveInDirection = locationAfterMoveInDirection(direction);
        int pacmanHalfSize = (this.size / 2);
        Point topLeft = new Point(locAfterMoveInDirection.x - pacmanHalfSize, locAfterMoveInDirection.y - pacmanHalfSize);
        Point topRight = new Point(locAfterMoveInDirection.x + pacmanHalfSize - 1, locAfterMoveInDirection.y - pacmanHalfSize);
        Point bottomLeft = new Point(locAfterMoveInDirection.x - pacmanHalfSize, locAfterMoveInDirection.y + pacmanHalfSize - 1);
        Point bottomRight = new Point(locAfterMoveInDirection.x + pacmanHalfSize - 1, locAfterMoveInDirection.y + pacmanHalfSize - 1);

        Point[] points = {topLeft, topRight, bottomLeft, bottomRight};
        for (int i = 0; i < points.length; i++) {
            int xCoord = points[i].x / 20;
            int yCoord = points[i].y / 20;
//            In case of teleportation
            xCoord = Math.max(xCoord, 0);
            xCoord = Math.min(xCoord, layout.length - 1);

            if (layout[yCoord][xCoord] == 1 || layout[yCoord][xCoord] == 2) {
                return true;
            }
        }
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
