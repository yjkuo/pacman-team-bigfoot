package edu.rice.comp504.model.strategy.ghost;

import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.Pacman;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class WalkStrategy implements IUpdateGhostStrategy {
    private static IUpdateStrategy singleton;
    private int[][] layout;

    private WalkStrategy(int[][] layout) {
        this.layout = layout;
    }

    /**
     * Make a walk strategy.  There is only one (singleton).
     *
     * @return A walk strategy
     */
    public static IUpdateStrategy makeStrategy(int[][] layout) {
        if (singleton == null) {
            singleton = new WalkStrategy(layout);
        }

        return singleton;
    }

    @Override
    public void updateState(ACharacter pacman, ACharacter context) {
        if (context == null || !context.getName().equals("ghost")) {
            return;
        }
        if (pacman.getName().equals("pacman")) {
            Pacman pacmanTemp = (Pacman) pacman;
            int newDirection = -1;
            Point currentLoc = context.getLoc();
            Point newLoc = currentLoc;

            int dir = context.getDirection() + 1;
            ArrayList<Integer> directions = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                if (!context.detectCollisionWithWalls(i - 1, layout)) {
                    directions.add(i);
                }
            }
            if (dir <= 0 || context.detectCollisionWithWalls(dir, layout)) {
                newDirection = directions.get(new Random().nextInt(directions.size()));
                if (directions.size() > 1) {
                    while (newDirection == dir + 2 || newDirection == (dir + 2) % 4) {
                        newDirection = directions.get(new Random().nextInt(directions.size()));
                    }
                }
            } else if (directions.size() > 2) {
                newDirection = directions.get(new Random().nextInt(directions.size()));
                while (newDirection == dir + 2 || newDirection == (dir + 2) % 4) {
                    newDirection = directions.get(new Random().nextInt(directions.size()));
                }
            } else {
                newDirection = dir;
            }

            // Apply new direction to update the state. Invalid direction of -1 if cannot move (should not be possible).
            context.setDirection(newDirection - 1);
            switch (newDirection) {
                case 1:
                    newLoc = new Point(currentLoc.x - context.getVel(), currentLoc.y);
                    break;
                case 2:
                    newLoc = new Point(currentLoc.x, currentLoc.y - context.getVel());
                    break;
                case 3:
                    newLoc = new Point(currentLoc.x + context.getVel(), currentLoc.y);
                    break;
                case 4:
                    newLoc = new Point(currentLoc.x, currentLoc.y + context.getVel());
                    break;
                default:
                    break;
            }
            context.setLoc(newLoc);
        }
    }

    @Override
    public String getName() {
        return "walk";
    }
}
