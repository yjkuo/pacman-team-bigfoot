package edu.rice.comp504.model.cmd;

/**
 * Factory that will make commands based on the input.
 */
public class CmdFactory {
    private static CmdFactory singleton;

    private CmdFactory() {
    }

    /**
     * Only makes 1 command factory.
     * @return The command factory.
     */
    public static CmdFactory makeCmdFactory() {
        if (singleton == null ) {
            singleton = new CmdFactory();
        }
        return singleton;
    }

    /**
     * Make a command based on input type.
     * @param type The type of the command.
     * @return The corresponding command.
     */
    public ICharacterCmd makeCmd(String type) {
        //TODO
        if (type == "Update") {
            return UpdateCmd.makeCmd();
        }
        return null;
    }
}
