package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.cmd.CmdFactory;
import edu.rice.comp504.model.cmd.ICharacterCmd;
import edu.rice.comp504.model.strategy.ghost.*;

/**
 * Factory that will make strategies based on the input.
 */
public class StrategyFactory {
    private static StrategyFactory singleton;

    private StrategyFactory() {
    }

    /**
     * Only makes 1 strategy factory.
     * @return The strategy factory.
     */
    public static StrategyFactory makeStrategyFactory() {
        if (singleton == null ) {
            singleton = new StrategyFactory();
        }
        return singleton;
    }

    /**
     * Make a strategy based on input type.
     * @param type The type of the strategy.
     * @return The corresponding strategy.
     */
    public IUpdateStrategy makeStrategy(String type, int[][] layout) {
        IUpdateStrategy strategy = null;
        switch (type) {
            case "walk":
                strategy = WalkStrategy.makeStrategy(layout);
                break;
            case "chase":
                strategy = ChaseStrategy.makeStrategy(layout);
                break;
            case "retreat":
                strategy = RetreatStrategy.makeStrategy(layout);
                break;
            case "goBackToBase":
                strategy = GoBackToBaseStrategy.makeStrategy(layout);
                break;
            case "leaveTheBase":
                strategy = LeaveTheBaseStrategy.makeStrategy(layout);
                break;
            default:
                break;
        }
        return strategy;
    }
}
