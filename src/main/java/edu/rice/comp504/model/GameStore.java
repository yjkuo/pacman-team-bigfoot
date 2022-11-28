package edu.rice.comp504.model;

import edu.rice.comp504.model.cmd.ICharacterCmd;
import edu.rice.comp504.model.cmd.SwitchStrategyCmd;
import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.AItem;
import edu.rice.comp504.model.object.AObject;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.strategy.IUpdatePacmanStrategy;
import edu.rice.comp504.model.strategy.IUpdateStrategy;
import edu.rice.comp504.model.strategy.PacmanStrategy;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class GameStore {
    private ACharacter pacman;
    private List<AItem> items;
    private List<Ghost> ghosts;
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
//    private PropertyChangeSupport pcs;
    public static final int updatePeriod = 60;
    private int timeElapsed = 0;
    private int endTime;
    private int[][] layout;
    private int passageWidth = 20;
    private IUpdateStrategy pacmanStrategy;
    Point pacmanStartLoc = new Point(14 * passageWidth + passageWidth/2,17 * passageWidth + passageWidth / 2);

    /**
     * Constructor.
     */
    public GameStore() {
//        pcs = new PropertyChangeSupport(this);
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
        pacmanStrategy = new PacmanStrategy(layout);
        int vel = 5;
        this.pacman = new ACharacter("pacman", pacmanStartLoc, vel, "pacman", pacmanStrategy,
        2, passageWidth - 4);

        items = new ArrayList<>();
        for (int i = 0; i < this.layout.length; i++) {
            for (int j = 0; j < this.layout[0].length; j++) {
                if (this.layout[i][j] == 0) {
                    Point smallDotLoc = new Point(j * passageWidth + passageWidth/2, i * passageWidth + passageWidth/2);
                    AItem smallDot = new AItem("smallDot", smallDotLoc, "white", 10, 3);
                    items.add(smallDot);
                }
                else if (this.layout[i][j] == 3) {
                    Point largeDotLoc = new Point(j * passageWidth + passageWidth/2, i * passageWidth + passageWidth/2);
                    AItem largeDot = new AItem("bigDot", largeDotLoc, "white", 50, 6);
                    items.add(largeDot);
                }
            }
        }
    }

    /**
     * Initialize a new game with a level and settings.
     */
    public void init(int gameLevel, int numberOfGhosts, int lives) {
        this.levelCount = gameLevel;
        this.numberOfGhosts = numberOfGhosts;
        this.lives = lives;
        currentScore = 0;
        eatenDots = 0;
        ghostScore = 200;
        portals = new int[2];
        numberOfFruits = 0;
        //TODO Add more intialization
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
    public List<Ghost> getGhosts() {
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
     * Remove all PropertyChangeListener.
     */
    public void removeAll() {
        //TODO
    }
}
