package goofy.command;

import goofy.exception.GoofyException;
import goofy.storage.Storage;
import goofy.task.Event;
import goofy.task.Task;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents a command to add an event task to the task list.
 */
public class AddEventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    /**
     * Creates a new AddEventCommand with the given description and time range.
     *
     * @param description Description of the event task.
     * @param from Start time of the event.
     * @param to End time of the event.
     */
    public AddEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /**
     * Executes the add event command by adding a new event task.
     *
     * @param tasks TaskList to operate on.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance for saving tasks.
     * @throws GoofyException If there is an error saving the task.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GoofyException {
        Task task = new Event(description, from, to);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        storage.saveTasks(tasks.getTasks());
    }
}