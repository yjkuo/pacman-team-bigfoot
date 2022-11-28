package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.cmd.CmdFactory;
import edu.rice.comp504.model.cmd.ICharacterCmd;

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
    public IUpdateStrategy makeStrategy(String type) {
        //TODO
        return null;
    }
}
