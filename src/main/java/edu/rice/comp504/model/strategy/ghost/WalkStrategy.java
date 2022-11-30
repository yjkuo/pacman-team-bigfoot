package edu.rice.comp504.model.strategy.ghost;

import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

public class WalkStrategy implements IUpdateGhostStrategy{
    private static IUpdateStrategy singleton;

    /**
     * Make a walk strategy.  There is only one (singleton).
     * @return A walk strategy
     */
    public static IUpdateStrategy makeStrategy() {
        if (singleton == null) {
            singleton = new WalkStrategy();
        }

        return singleton;
    }

    @Override
    public void updateState(ACharacter pacman, ACharacter ghost) {

    }

    @Override
    public String getName() {
        return "walk";
    }
}
