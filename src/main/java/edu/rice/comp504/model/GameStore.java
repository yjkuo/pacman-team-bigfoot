package edu.rice.comp504.model;

import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.AItem;
import edu.rice.comp504.model.object.AObject;
import edu.rice.comp504.model.strategy.IUpdatePacmanStrategy;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class GameStore {
    private ACharacter pacman;
    private List<ACharacter> ghosts;
    private List<AObject> allObjects;
    private int lives;
    private int currentScore;
    private int eatenDots;
    private int levelCount;
    private int numberOfGhosts;
    private int numberOfFruits;
    private int ghostScore = 200;
    private final int ghostFlashingTime = 10000; // how long ghost keeps flashing
    private final int maxGhosts = 4;
    private transient int[] portals = new int[2];
    private PropertyChangeSupport pcs;
    public static final int updatePeriod = 60;
    private int timeElapsed = 0;
    private int endTime;
    private int[][] layout;
    private int passageWidth = 20;

    /**
     * Constructor.
     */
    public GameStore() {
        pcs = new PropertyChangeSupport(this);
        allObjects = new ArrayList<>();
        this.layout = new int[][]{
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,},
                {1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,},
                {1,0,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,},
                {1,0,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,},
                {1,0,1,1,1,0,0,0,0,0,1,1,1,1,0,1,1,1,1,1,1,1,0,0,0,0,0,1,},
                {1,0,1,1,1,0,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,},
                {1,0,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,1,},
                {1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,},
                {1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,},
                {1,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1,},
                {1,1,1,1,1,1,0,1,1,0,1,1,1,2,2,1,1,1,0,1,1,0,1,1,1,1,1,1,},
                {1,1,1,1,1,1,0,1,1,0,1,2,2,2,2,2,2,1,0,1,1,0,1,1,1,1,1,1,},
                {4,4,4,4,4,4,0,0,0,4,1,2,2,2,2,2,2,1,0,0,0,0,4,4,4,4,4,4,},
                {1,1,1,1,1,1,0,1,1,4,1,2,2,2,2,2,2,1,4,1,1,0,1,1,1,1,1,1,},
                {1,1,1,1,1,1,0,1,1,4,1,1,1,1,1,1,1,1,4,1,1,0,1,1,1,1,1,1,},
                {1,1,1,1,1,1,0,1,1,4,1,1,1,1,1,1,1,1,4,1,1,0,1,1,1,1,1,1,},
                {1,0,0,0,0,0,0,0,0,4,4,4,4,4,4,4,4,4,4,0,0,0,0,0,0,0,0,1,},
                {1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,},
                {1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,0,1,},
                {1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,},
                {1,0,1,1,1,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,},
                {1,0,1,1,1,1,1,0,1,1,1,0,0,0,0,1,1,1,0,1,1,1,1,0,1,1,0,1,},
                {1,0,0,0,0,0,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1,0,1,1,0,1,},
                {1,0,1,1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1,0,1,1,0,1,},
                {1,0,1,1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1,0,1,1,0,1,},
                {1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        Point loc = new Point(0,0);
        int vel = 5;
//        this.pacman = new ACharacter("pacman", loc, Point vel, "pacman", IUpdatePacmanStrategy updateStrategy,
//        "right", "16");
    }

    /**
     * Initialize a new game with a level and settings.
     */
    public List<AObject> init(int gameLevel, int numberOfGhosts, int lives) {
        this.levelCount = gameLevel;
        this.numberOfGhosts = numberOfGhosts;
        this.lives = lives;
        currentScore = 0;
        eatenDots = 0;
        ghostScore = 200;
        portals = new int[2];
        numberOfFruits = 0;
        //TODO Add more intialization
        return allObjects;
    }

    /**
     * Get the pacman.
     * @return the pacman.
     */
    public ACharacter getPacman() {
        return pacman;
    }

    /**
     * Get the ghosts.
     * @return the ghosts.
     */
    public List<ACharacter> getGhosts() {
        return ghosts;
    }

    /**
     * Get the lives.
     * @return the lives.
     */
    public int getLives() {
        //TODO
        return 0;
    }

    /**
     * Get the current score.
     * @return the current score.
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Set the current score.
     * @param currentScore the current score.
     */
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }


    /**
     * Get the number of fruits.
     * @return the number of fruits.
     */
    public int getNumberOfFruits() {
        return numberOfFruits;
    }


    /**
     * If the game is over.
     * @return if the game is over.
     */
    public boolean isGameOver() {
        //TODO
        return false;
    }


    /**
     * Set the game settings.
     * @param gameLevel Game level.
     * @param numGhosts Number of ghosts.
     * @param numLives  Number of lives.
     */
    public void setGameParameters(int gameLevel, int numGhosts, int numLives) {
        levelCount = gameLevel;
        numberOfGhosts = numGhosts;
        lives = numLives;
//        init();
    }

    /**
     * Get the ghost score.
     * @return Ghost score.
     */
    public int getGhostScore() {
        return ghostScore;
    }

    /**
     * Set the ghost score.
     * @param ghostScore Ghost score.
     */
    public void setGhostScore(int ghostScore) {
        this.ghostScore = ghostScore;
    }

    /**
     * Remove a dot.
     * @param dot dot needs to be removed
     * @param isEaten if the dot is eaten
     */
    public void removeDot(AItem dot, boolean isEaten) {
        //TODO
    }

    /**
     * Remove a big dot.
     * @param dot big dot needs to be removed
     * @param isEaten if the big dot is eaten
     */
    public void removeBigDot(AItem dot, boolean isEaten) {
        //TODO
    }

    /**
     * Remove a fruit.
     * @param fruit fruit needs to be removed
     * @param isEaten if the fruit is eaten
     */
    public void removeFruit(AItem fruit, boolean isEaten) {
        //TODO
    }

    /**
     * Update the state of the store.
     */
    public List<AObject> updateStore(String direction) {
        //TODO
        return allObjects;
    }

    /**
     * Remove all PropertyChangeListener.
     */
    public void removeAll() {
        //TODO
    }
}
