package goofy;

import goofy.task.Deadline;
import goofy.task.Event;
import goofy.task.Task;
import goofy.task.Todo;

/**
 * Parses user input and executes the corresponding commands.
 * Responsible for interpreting raw user input and delegating
 * to the appropriate task operations.
 */
public class Parser {
    private static final int TODO_COMMAND_LENGTH = 5;
    private static final int DEADLINE_COMMAND_LENGTH = 9;
    private static final int EVENT_COMMAND_LENGTH = 6;
    private static final int MARK_COMMAND_LENGTH = 5;
    private static final int UNMARK_COMMAND_LENGTH = 7;

    /**
     * Parses and executes the given user input command.
     *
     * @param input User input string.
     * @param tasks TaskList to operate on.
     * @param ui Ui instance for displaying results.
     * @throws GoofyException If the command is invalid or input is malformed.
     */
    public void parseCommand(String input, TaskList tasks, Ui ui) throws GoofyException {
        if (input.equalsIgnoreCase("list")) {
            ui.showTaskList(tasks.getTasks());
        } else if (input.equalsIgnoreCase("mark") || input.startsWith("mark ")) {
            parseMark(input, tasks, ui);
        } else if (input.equalsIgnoreCase("unmark") || input.startsWith("unmark ")) {
            parseUnmark(input, tasks, ui);
        } else if (input.equalsIgnoreCase("todo") || input.startsWith("todo ")) {
            parseTodo(input, tasks, ui);
        } else if (input.equalsIgnoreCase("deadline") || input.startsWith("deadline ")) {
            parseDeadline(input, tasks, ui);
        } else if (input.equalsIgnoreCase("event") || input.startsWith("event ")) {
            parseEvent(input, tasks, ui);
        } else {
            throw new GoofyException("\"" + input + "\"?? A-hyuck, I have NO idea what that means! "
                    + "Try one of these instead: todo, deadline, event, list, mark, unmark, bye.");
        }
    }

    /**
     * Parses and executes a todo command.
     *
     * @param input User input string.
     * @param tasks TaskList to add the task to.
     * @param ui Ui instance for displaying results.
     * @throws GoofyException If the todo description is empty.
     */
    private void parseTodo(String input, TaskList tasks, Ui ui) throws GoofyException {
        if (input.trim().equalsIgnoreCase("todo")) {
            throw new GoofyException("Gawrsh, a todo with no name? Even I'm not that forgetful! "
                    + "Usage: todo <description>");
        }
        String description = input.substring(TODO_COMMAND_LENGTH).trim();
        if (description.isEmpty()) {
            throw new GoofyException("Gawrsh, a todo with no name? Even I'm not that forgetful! "
                    + "Usage: todo <description>");
        }
        Task task = new Todo(description);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
    }

    /**
     * Parses and executes a deadline command.
     *
     * @param input User input string.
     * @param tasks TaskList to add the task to.
     * @param ui Ui instance for displaying results.
     * @throws GoofyException If the format is invalid or fields are empty.
     */
    private void parseDeadline(String input, TaskList tasks, Ui ui) throws GoofyException {
        if (input.trim().equalsIgnoreCase("deadline")) {
            throw new GoofyException("Whoa there! A deadline needs more info than that! "
                    + "Usage: deadline <description> /by <date>");
        }
        String details = input.substring(DEADLINE_COMMAND_LENGTH).trim();
        if (!details.contains("/by")) {
            throw new GoofyException("You forgot the /by part! How will I know when it's due?! "
                    + "Usage: deadline <description> /by <date>");
        }

        String[] parts = details.split("/by", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new GoofyException("Gawrsh, both a description AND a date are needed! "
                    + "Usage: deadline <description> /by <date>");
        }

        Task task = new Deadline(description, by);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
    }

    /**
     * Parses and executes an event command.
     *
     * @param input User input string.
     * @param tasks TaskList to add the task to.
     * @param ui Ui instance for displaying results.
     * @throws GoofyException If the format is invalid or fields are empty.
     */
    private void parseEvent(String input, TaskList tasks, Ui ui) throws GoofyException {
        if (input.trim().equalsIgnoreCase("event")) {
            throw new GoofyException("An event with no details?! That's like a party with no snacks! "
                    + "Usage: event <description> /from <start> /to <end>");
        }
        String details = input.substring(EVENT_COMMAND_LENGTH).trim();
        if (!details.contains("/from") || !details.contains("/to")) {
            throw new GoofyException("Whoa, I need both /from AND /to or I'll get lost! "
                    + "Usage: event <description> /from <start> /to <end>");
        }

        String[] parts = details.split("/from", 2);
        String description = parts[0].trim();
        String[] timeParts = parts[1].split("/to", 2);
        String from = timeParts[0].trim();
        String to = timeParts[1].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new GoofyException("Gawrsh, ya can't leave any blanks! "
                    + "Usage: event <description> /from <start> /to <end>");
        }

        Task task = new Event(description, from, to);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
    }

    /**
     * Parses and executes a mark command.
     *
     * @param input User input string.
     * @param tasks TaskList containing the task to mark.
     * @param ui Ui instance for displaying results.
     * @throws GoofyException If the task number is invalid.
     */
    private void parseMark(String input, TaskList tasks, Ui ui) throws GoofyException {
        if (input.trim().equalsIgnoreCase("mark")) {
            throw new GoofyException("Mark WHAT exactly?! Give me a number! "
                    + "Usage: mark <task number>");
        }
        try {
            int taskNumber = Integer.parseInt(input.substring(MARK_COMMAND_LENGTH).trim());
            tasks.markTask(taskNumber);
            ui.showTaskMarkedAsDone(tasks.getTask(taskNumber - 1));
        } catch (NumberFormatException e) {
            throw new GoofyException("A-hyuck, that doesn't look like a number to me! "
                    + "Usage: mark <task number>");
        }
    }

    /**
     * Parses and executes an unmark command.
     *
     * @param input User input string.
     * @param tasks TaskList containing the task to unmark.
     * @param ui Ui instance for displaying results.
     * @throws GoofyException If the task number is invalid.
     */
    private void parseUnmark(String input, TaskList tasks, Ui ui) throws GoofyException {
        if (input.trim().equalsIgnoreCase("unmark")) {
            throw new GoofyException("Unmark WHAT exactly?! Give me a number! "
                    + "Usage: unmark <task number>");
        }
        try {
            int taskNumber = Integer.parseInt(input.substring(UNMARK_COMMAND_LENGTH).trim());
            tasks.unmarkTask(taskNumber);
            ui.showTaskMarkedAsNotDone(tasks.getTask(taskNumber - 1));
        } catch (NumberFormatException e) {
            throw new GoofyException("A-hyuck, that doesn't look like a number to me! "
                    + "Usage: unmark <task number>");
        }
    }
}