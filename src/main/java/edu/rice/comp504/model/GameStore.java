package edu.rice.comp504.model;

import edu.rice.comp504.model.cmd.CmdFactory;
import edu.rice.comp504.model.cmd.ICharacterCmd;
import edu.rice.comp504.model.cmd.SwitchStrategyCmd;
import edu.rice.comp504.model.object.*;
import edu.rice.comp504.model.strategy.IUpdateStrategy;
import edu.rice.comp504.model.strategy.PacmanStrategy;
import edu.rice.comp504.model.strategy.StrategyFactory;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameStore {
    private Pacman pacman;
    private List<AItem> items;
    private List<Ghost> ghosts;
    private int lives;
    private int initialLives;
    private int currentScore;
    private int eatenDots;
    private int levelCount;
    private int numberOfGhosts;
    private int numberOfFruits;
    private int ghostScore = 200;
    private int singleGhostScore = 200;
    private final int ghostFlashingTime = 10000; // how long ghost keeps flashing
    private int bigDotTotalTime = 100;
    private int bigDotTimeLeft = 0;
    private final int maxGhosts = 4;
    public static final int updatePeriod = 60;
    private int timeElapsed = 0;
    private int endTime;
    private int timeStamp;
    private int nextLevelFreezeTimeRemaining;
    private int[][] layout;
    private int passageWidth = 20;

    private boolean gameFreeze = false;
    private boolean nextLevelFreeze = false;

    private IUpdateStrategy pacmanStrategy;
    Point pacmanStartLoc = new Point(14 * passageWidth + passageWidth / 2,17 * passageWidth + passageWidth / 2);
    Point yellowGhostStartLoc = new Point(13 * passageWidth + passageWidth / 2, 12 * passageWidth + passageWidth / 2);
    Point pinkGhostStartLoc = new Point(14 * passageWidth + passageWidth / 2, 12 * passageWidth + passageWidth / 2);
    Point blueGhostStartLoc = new Point(13 * passageWidth + passageWidth / 2, 13 * passageWidth + passageWidth / 2);
    Point redGhostStartLoc = new Point(14 * passageWidth + passageWidth / 2, 13 * passageWidth + passageWidth / 2);


    Map<Integer, String> ghostIndexColorMap  = new HashMap<Integer, String>(){
        {
            put(0, "orange");
            put(1, "pink");
            put(2, "blue");
            put(3, "red");
        }
    };

    Map<String, Point> ghostColorStartLocMap  = new HashMap<String, Point>(){
        {
            put("orange", yellowGhostStartLoc);
            put("pink", pinkGhostStartLoc);
            put("blue", blueGhostStartLoc);
            put("red", redGhostStartLoc);
        }
    };

    /**
     * Constructor.
     */
    public GameStore() {
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

        ghosts = new ArrayList<>();
        items = new ArrayList<>();
        pacmanStrategy = new PacmanStrategy(layout);
        this.pacman = new Pacman("pacman", pacmanStartLoc, 5, "pacman", pacmanStrategy,
                2, passageWidth);
    }

    /**
     * Initialize a new game with a level and settings.
     */
    public void init(int gameLevel, int numberOfGhosts, int lives) {
        this.levelCount = gameLevel;
        this.numberOfGhosts = numberOfGhosts;
        this.lives = lives;
        this.initialLives = lives;
        currentScore = 0;
        eatenDots = 0;
        numberOfFruits = 0;
        timeElapsed = 0;
        gameFreeze = false;

        bigDotTotalTime = gameLevel == 1 ? 100 : 75;

        resetPacman();
        resetItems();
        resetGhosts();
    }

    private void genFruits() {
        Random rand = new Random();
        int i = rand.nextInt(layout.length);
        int j = rand.nextInt(layout[0].length);
        while (layout[i][j] != 0) {
            i = rand.nextInt(layout.length);
            j = rand.nextInt(layout[0].length);
        }
        Point fruitLoc = new Point(j * passageWidth + passageWidth / 2, i * passageWidth + passageWidth / 2);
        AItem fruit = new AItem("fruit", fruitLoc, "", 100, passageWidth - 6);
        items.add(fruit);
        numberOfFruits++;
    }

    private void resetPacman() {
        pacman.setLoc(pacmanStartLoc);
        pacman.setDirection(2);
    }

    private void resetGhosts() {
        this.ghosts.clear();
        IUpdateStrategy leaveTheBaseStrategy = StrategyFactory.makeStrategyFactory().makeStrategy("leaveTheBase", layout);
        int vel = levelCount == 1 ? 4 : 5;
        for (int i = 0; i < this.numberOfGhosts; i++) {
            String ghostColor = ghostIndexColorMap.get(i);
            Point ghostStartLoc = ghostColorStartLocMap.get(ghostColor);
            Ghost ghost = new Ghost("ghost", ghostStartLoc, vel, ghostColor, leaveTheBaseStrategy, 2, 20, false, false, 0);

            this.ghosts.add(ghost);
        }
    }

    private void resetItems() {
        items.clear();
        for (int i = 0; i < this.layout.length; i++) {
            for (int j = 0; j < this.layout[0].length; j++) {
                if (this.layout[i][j] == 0) {
                    Point smallDotLoc = new Point(j * passageWidth + passageWidth / 2, i * passageWidth + passageWidth / 2);
                    AItem smallDot = new AItem("smallDot", smallDotLoc, "white", 10, 3);
                    items.add(smallDot);
                } else if (this.layout[i][j] == 3) {
                    Point largeDotLoc = new Point(j * passageWidth + passageWidth / 2, i * passageWidth + passageWidth / 2);
                    AItem largeDot = new AItem("bigDot", largeDotLoc, "white", 50, 6);
                    items.add(largeDot);
                }
            }
        }
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
        items.remove(dot);
        if (dot.getName().equals("bigDot")) {
            bigDotEaten();
        }
        currentScore += dot.getScore();
        if (!dot.getName().equals("fruit")) {
            eatenDots++;
            if (eatenDots == 244) {
                nextLevelFreeze = true;
                nextLevelFreezeTimeRemaining = 20;
            }
        } else {
            numberOfFruits--;
        }
    }

    /**
     * Change ghost after big dot eaten.
     */
    public void bigDotEaten() {
        for (Ghost ghost: ghosts) {
            if (!ghost.getUpdateStrategy().getName().equals("goBackToBase") && !ghost.getUpdateStrategy().getName().equals("leaveTheBase")) {
                ghost.setUpdateStrategy(StrategyFactory.makeStrategyFactory().makeStrategy("retreat", layout));
                ghost.setFlashing(true);
            }
        }
        bigDotTimeLeft = bigDotTotalTime;
    }

    /**
     * Update the game store.
     */
    public void update(int direction) {
        if (nextLevelFreeze) {
            nextLevelFreezeTimeRemaining--;
            if (nextLevelFreezeTimeRemaining <= 0) {
                nextLevelFreeze = false;
                init(2, numberOfGhosts, initialLives);
            }
            return;
        }
        timeElapsed++;
        if (gameFreeze) {
            if (timeElapsed - timeStamp == 20) {
                resetGhosts();
                resetPacman();
                gameFreeze = false;
            }
            return;
        }

        if (bigDotTimeLeft > 0) {
            bigDotTimeLeft--;
            if (bigDotTimeLeft == 0) {
                ghostScore = singleGhostScore;
                for (Ghost ghost : ghosts) {
                    if (ghost.isFlashing()) {
                        ghost.setFlashing(false);
                        ghost.setStrategyToDefault(layout);
                    }
                }
            }
        }

        pacman.setNextDirection(direction);
        pacman.executeCommand(CmdFactory.makeCmdFactory().makeCmd("Update"));
        for (Ghost ghost : ghosts) {
            ghost.executeCommand(CmdFactory.makeCmdFactory().makeCmd("Update"), pacman);
        }

        for (Ghost ghost : ghosts) {
            if (pacman.detectCollisionObj(ghost)) {
                if (ghost.getUpdateStrategy().getName().equals("retreat")) {
                    ghost.setDead(true);
                    ghost.setFlashing(false);
                    ghost.setUpdateStrategy(StrategyFactory.makeStrategyFactory().makeStrategy("goBackToBase", layout));
                    currentScore += ghostScore;
                    ghostScore *= 2;
                } else if (ghost.getUpdateStrategy().getName().equals("goBackToBase")) {
                    // TODO important
                } else {
                    this.lives--;
                    gameFreeze = true;
                    timeStamp = timeElapsed;
                    return;
                }
            }
        }

        AItem eaten = null;
        for (AItem item : items) {
            if (pacman.detectCollisionObj(item)) {
                eaten = item;
                break;
            }
        }
        if (eaten != null) {
            removeDot(eaten, true);
        }

        if (timeElapsed % 150 == 0) {
            timeElapsed = 0;
            if (numberOfFruits < 2) {
                genFruits();
            }
        }
    }
}
