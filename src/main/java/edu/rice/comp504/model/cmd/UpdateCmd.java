package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.Pacman;

public class UpdateCmd implements ICharacterCmd{
    private static UpdateCmd singleton;

    private UpdateCmd() {
    }

    /**
     * Only makes 1 update command.
     * @return The update command.
     */
    public static UpdateCmd makeCmd() {
        if (singleton == null ) {
            singleton = new UpdateCmd();
        }
        return singleton;
    }

    @Override
    public void execute(ACharacter context) {
        context.getUpdateStrategy().updateState(context, context);
    }

    @Override
    public void execute(ACharacter pacman, ACharacter context) {
        if (context != null) {
            if (pacman.getName().equals("pacman")) {
                Pacman pacmanTemp = (Pacman) pacman;
                if (context.getName().equals("pacman")) {
                    ACharacter character = ((ACharacter) context);
                    character.getUpdateStrategy().updateState(pacmanTemp, character);
                } else if (context.getName().equals("ghost")) {
                    ACharacter character = ((ACharacter) context);
                    ACharacter pac = ((ACharacter) pacman);
                    Pacman pacmanObj = (Pacman) pac;
                    if (pacmanObj.getDeadState() == -1) {
                        character.getUpdateStrategy().updateState(pacmanTemp, character);
                    } else if (pacmanObj.getDeadState() == 12) {
                        Ghost ghost = (Ghost) context;
                        ghost.setStartLoc();
                    }
                }
            }
        }
    }
}
