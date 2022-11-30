package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.AObject;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.strategy.IUpdateStrategy;
import edu.rice.comp504.model.strategy.StrategyFactory;

public class SwitchStrategyCmd implements ICharacterCmd{
    private IUpdateStrategy strategyTo;


    /**
     * Constructor of the switch command.
     */
    public SwitchStrategyCmd(String strategyTo, int[][] layout) {
        this.strategyTo = StrategyFactory.makeStrategyFactory().makeStrategy(strategyTo, layout);
    }


    /**
     * Execute the command and set the ball to the desired strategy.
     * @param context The receiver paint object on which the command is executed.
     */
    @Override
    public void execute(ACharacter context) {
        if (context != null && context.getName().equals("ghost")) {
            Ghost ghost = ((Ghost) context);
            ((Ghost) context).setUpdateStrategy(strategyTo);
        }
    }

    @Override
    public void execute(ACharacter pacman, ACharacter context) {

    }
}
