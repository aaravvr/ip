package goofy.command;

import java.util.ArrayList;

import goofy.storage.Storage;
import goofy.task.Task;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents a command to find tasks whose descriptions contain a given keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a new FindCommand with the given keyword.
     *
     * @param keyword Keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for tasks matching the keyword.
     *
     * @param tasks TaskList to search.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance (not used by this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks.getTasks()) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        ui.showMatchingTasks(matchingTasks);
    }
}