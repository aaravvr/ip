package goofy.command;

import goofy.storage.Storage;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents a command to exit the Goofy application.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command by displaying the goodbye message.
     *
     * @param tasks TaskList to operate on.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance for saving tasks.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Returns true as this command exits the application.
     *
     * @return True always.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}