package goofy.command;

import goofy.storage.Storage;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks TaskList to operate on.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance for saving tasks.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks.getTasks());
    }
}