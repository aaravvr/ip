package goofy.command;

import goofy.exception.GoofyException;
import goofy.storage.Storage;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents an executable command in the Goofy application.
 * All specific command types extend this abstract class.
 */
public abstract class Command {

    /**
     * Executes the command using the given task list, UI, and storage.
     *
     * @param tasks TaskList to operate on.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance for saving tasks.
     * @throws GoofyException If the command encounters an error during execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws GoofyException;

    /**
     * Returns whether this command should exit the application.
     *
     * @return True if the application should exit, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}