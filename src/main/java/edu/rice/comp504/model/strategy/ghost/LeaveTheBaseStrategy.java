package edu.rice.comp504.model.strategy.ghost;

import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;

public class LeaveTheBaseStrategy implements IUpdateGhostStrategy{
    private static IUpdateStrategy singleton;
    private int[][] layout;
    private int passageWidth = 20;

    private LeaveTheBaseStrategy(int[][] layout) {
        this.layout = layout;
    }

    private boolean detectCollisionWithWallAfterLeavingBase(ACharacter ghost) {
        Point loc = ghost.getLoc();
        int ghostHalfSize = ghost.getSize() / 2;
        if ((loc.y - ghostHalfSize) % 20 == 0 ) {
            if (layout[(loc.y - ghostHalfSize) / 20 - 1][loc.x / 20] == 1) {
                return true;
            }
        }
        return false;
    }


    /**
     * Make a walk strategy.  There is only one (singleton).
     * @return A walk strategy
     */
    public static IUpdateStrategy makeStrategy(int[][] layout) {
        if (singleton == null) {
            singleton = new LeaveTheBaseStrategy(layout);
        }

        return singleton;
    }

    /**
     * Strategy update.
     */
    @Override
    public void updateState(ACharacter pacman, ACharacter ghost) {
        if (ghost != null) {
            if (ghost.getName().equals("ghost")) {
                int vel = ghost.getVel();
                for (int i = 0; i < vel; i++) {
                    if (!detectCollisionWithWallAfterLeavingBase(ghost)) {
                        Point loc = ghost.getLoc();
                        loc.y--;
                        ghost.setLoc(loc);
                    } else {
//                        System.out.println(ghost.getLoc());
                        Ghost gh = (Ghost) ghost;
                        gh.setStrategyToDefault(layout);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return "leaveTheBase";
    }
}
