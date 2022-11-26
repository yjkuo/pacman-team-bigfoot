package edu.rice.comp504.model.object;

import java.awt.*;

/**
 * This class represents an object in the game.
 */
public class AObject {
    private String name;
    private Point loc;
    private String color;

    /**
     * Constructor for the AObject class.
     *
     * @param loc   The location of the object on the canvas
     * @param name  The name of the object
     */
    public AObject(String name, Point loc, String color) {
        this.name = name;
        this.loc = loc;
        this.color = color;
    }

    /**
     * Get the ACharacter color.
     * @return ACharacter color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Set the ACharacter color.
     * @param color The new ACharacter color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Get the object name.
     * @return object name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the object name.
     * @param name The new object name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the object location.
     * @return The object location.
     */
    public Point getLoc() {
        return loc;
    }

    /**
     * Set the object location in the canvas. The origin (0,0) is the top left corner of the canvas.
     * @param loc  The object coordinate.
     */
    public void setLoc(Point loc) {
        this.loc = loc;
    }
}
