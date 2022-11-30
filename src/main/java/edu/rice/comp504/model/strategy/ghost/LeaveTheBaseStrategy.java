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

    private boolean detectCollisionWithWallsExceptBase(ACharacter ghost) {
        Point locAfterMoveInDirection = new Point((int) ghost.getLoc().getX(), (int) ghost.getLoc().getY());
        locAfterMoveInDirection.y--;
        int ghostHalfSize = (ghost.getSize() / 2) - 1;
        Point topLeft = new Point(locAfterMoveInDirection.x - ghostHalfSize, locAfterMoveInDirection.y - ghostHalfSize);
        Point topRight = new Point(locAfterMoveInDirection.x + ghostHalfSize, locAfterMoveInDirection.y - ghostHalfSize);
        Point bottomLeft = new Point(locAfterMoveInDirection.x - ghostHalfSize, locAfterMoveInDirection.y + ghostHalfSize);
        Point bottomRight = new Point(locAfterMoveInDirection.x + ghostHalfSize, locAfterMoveInDirection.y + ghostHalfSize);

        Point[] points = {topLeft, topRight, bottomLeft, bottomRight};
        for (int i = 0; i < points.length; i++) {
            int xCoord = points[i].x / 20;
            int yCoord = points[i].y / 20;
//            In case of teleportation
            xCoord = Math.max(xCoord, 0);
            xCoord = Math.min(xCoord, layout.length - 1);

            if (layout[yCoord][xCoord] == 1) {
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
    @Override
    public void updateState(ACharacter pacman, ACharacter ghost) {
        if (ghost != null) {
            if (ghost.getName().equals("ghost")) {
                int vel = ghost.getVel();
                for (int i = 0; i < vel; i++) {
                    if (!detectCollisionWithWallsExceptBase(ghost)) {
                        Point loc = ghost.getLoc();
                        loc.y--;
                        ghost.setLoc(loc);
                    }
                    else {
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
