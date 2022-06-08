package graphics.ui.menu;

import gamestates.Game;
import graphics.ui.Button;
import util.Commandable;

public class CloseButton extends Button {
    private Commandable secondCommand;
    public CloseButton(String text) {
        super(text);
        secondCommand = () -> {};
    }

    public CloseButton(String text, Commandable command) {
        super(text);
        secondCommand = command;
    }

    public Commandable getSecondCommand() {
        return secondCommand;
    }

    public void setSecondCommand(Commandable secondCommand) {
        this.secondCommand = secondCommand;
    }
}
