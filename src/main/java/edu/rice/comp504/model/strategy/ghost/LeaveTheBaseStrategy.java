package edu.rice.comp504.model.strategy.ghost;

import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;

public class LeaveTheBaseStrategy implements IUpdateGhostStrategy{
    private static IUpdateStrategy singleton;
    private int[][] layout;
    private int passageWidth = 20;

    private LeaveTheBaseStrategy(int[][] layout) {
        this.layout = layout;
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
        Point gate = new Point(14 * passageWidth + passageWidth/2, 12 * passageWidth + passageWidth/2);
        if (ghost != null) {
            if (ghost.getName().equals("ghost")) {
                Point currentLoc = ghost.getLoc();

            }
        }
    }

    @Override
    public String getName() {
        return null;
    }
}
