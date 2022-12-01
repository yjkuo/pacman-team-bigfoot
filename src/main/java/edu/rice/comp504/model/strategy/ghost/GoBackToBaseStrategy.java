package edu.rice.comp504.model.strategy.ghost;

import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.Pacman;
import edu.rice.comp504.model.strategy.IUpdateStrategy;
import edu.rice.comp504.model.strategy.StrategyFactory;

import java.awt.*;
import java.util.ArrayList;

public class GoBackToBaseStrategy implements IUpdateGhostStrategy {
    private static IUpdateStrategy singleton;
    private int[][] layout;

    private GoBackToBaseStrategy(int[][] layout) {
        this.layout = layout;
    }

    /**
     * Make a walk strategy.  There is only one (singleton).
     *
     * @return A walk strategy
     */
    public static IUpdateStrategy makeStrategy(int[][] layout) {
        if (singleton == null) {
            singleton = new GoBackToBaseStrategy(layout);
        }

        return singleton;
    }

    @Override
    public void updateState(ACharacter pacman, ACharacter context) {
        if (context != null) {
            if (pacman.getName().equals("pacman")) {
                if (context.getName().equals("ghost")) {
                    Point currentLoc = context.getLoc();
                    Point newLoc = currentLoc;
                    Point originalLoc = context.getOriginalLoc();
                    int dir = 0;
                    if (currentLoc.x < originalLoc.x) {
                        newLoc = new Point(currentLoc.x + context.getVel(), currentLoc.y);
                        dir = 2;
                    } else if (currentLoc.x > originalLoc.x) {
                        newLoc = new Point(currentLoc.x - context.getVel(), currentLoc.y);
                        dir = 0;
                    } else {
                        if (currentLoc.y < originalLoc.y) {
                            newLoc = new Point(currentLoc.x, currentLoc.y + context.getVel());
                            dir = 1;
                        } else if (currentLoc.y > originalLoc.y) {
                            newLoc = new Point(currentLoc.x, currentLoc.y - context.getVel());
                            dir = 3;
                        } else {
                            IUpdateStrategy leaveTheBase = StrategyFactory.makeStrategyFactory().makeStrategy("leaveTheBase", layout);
                            context.setUpdateStrategy(leaveTheBase);
                            ((Ghost) context).setDead(false);
                        }
                    }
                    context.setDirection(dir);
                    // Update ghost state.
                    context.setLoc(newLoc);
                }

            }
        }
    }

    @Override
    public String getName() {
        return "goBackToBase";
    }
}


