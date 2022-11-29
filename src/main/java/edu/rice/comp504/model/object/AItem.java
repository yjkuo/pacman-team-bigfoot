package edu.rice.comp504.model.object;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents the unmoving items in the game (small dots, big dots, and fruits).
 */
public class AItem extends AObject {
    private boolean isEaten;
    private int score;

    /**
     * Constructor.
     * @param name The name of the AItem
     * @param loc  The location of the AItem on the canvas
     * @param color The AItem color
     */
    public AItem(String name, Point loc, String color, int score, int size) {
        super(name, loc, color, size);
        this.isEaten = false;
        this.score = score;
    }

    /**
     * Get if the AItem color is eaten.
     * @return If AItem is eaten
     */
    public boolean isEaten() {
        return isEaten;
    }

    /**
     * Set if the AItem color is eaten.
     * @param eaten If AItem is eaten
     */
    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    /**
     * Get the AItem score.
     * @return AItem score
     */
    public int getScore() {
        return score;
    }
}
