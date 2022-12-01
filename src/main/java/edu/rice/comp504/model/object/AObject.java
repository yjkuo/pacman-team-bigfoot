package edu.rice.comp504.model.object;

import java.awt.*;

/**
 * This class represents an object in the game.
 */
public class AObject {
    private String name;
    private Point loc;
    private String color;

    int size;

    /**
     * Constructor for the AObject class.
     *
     * @param loc   The location of the object on the canvas
     * @param name  The name of the object
     */
    public AObject(String name, Point loc, String color, int size) {
        this.name = name;
        this.loc = new Point(loc.x, loc.y);
        this.color = color;
        this.size = size;
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
        return new Point(loc.x, loc.y);
    }

    /**
     * Set the object location in the canvas. The origin (0,0) is the top left corner of the canvas.
     * @param loc  The object coordinate.
     */
    public void setLoc(Point loc) {
        if (loc.x + this.getSize() / 2 >= 20 * 28) {
            loc.x = this.getSize() / 2;
        } else if (loc.x - this.getSize() / 2 <= 0) {
            loc.x = (20 * 28) - (this.getSize() / 2);
        }
        this.loc = new Point(loc.x, loc.y);;
    }

    /**
     * Get the size.
     * @return The size.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Set the size.
     * @param size The size.
     */
    public void setSize(int size) {
        this.size = size;
    }
}
