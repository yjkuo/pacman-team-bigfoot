package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.object.ACharacter;

/**
 * The ICharacterCmd is an interface used to pass commands to characters in the Pacman. The
 * character will execute the command when updating the state, interacting with another character or switch strategy (
 * for ghosts).
 */
public interface ICharacterCmd {
    /**
     * Execute the command.
     * @param context The receiver ACharacter on which the command is executed.
     */
    public void execute(ACharacter context);

    /**
     * Execute the command.
     * @param context The ACharacter on which the command is executed.
     * @param pacman The pacman on which the command is executed.
     */
    public void execute(ACharacter pacman, ACharacter context);
}
