package goofy.ui;

import goofy.command.AddDeadlineCommand;
import goofy.command.AddEventCommand;
import goofy.command.AddTodoCommand;
import goofy.command.Command;
import goofy.command.DeleteCommand;
import goofy.command.ExitCommand;
import goofy.command.ListCommand;
import goofy.command.MarkCommand;
import goofy.command.UnmarkCommand;
import goofy.exception.GoofyException;

/**
 * Parses user input and returns the corresponding command.
 * Responsible for interpreting raw user input and creating
 * the appropriate Command object.
 */
public class Parser {
    private static final int TODO_COMMAND_LENGTH = 5;
    private static final int DEADLINE_COMMAND_LENGTH = 9;
    private static final int EVENT_COMMAND_LENGTH = 6;
    private static final int MARK_COMMAND_LENGTH = 5;
    private static final int UNMARK_COMMAND_LENGTH = 7;
    private static final int DELETE_COMMAND_LENGTH = 7;

    /**
     * Parses the given user input and returns the corresponding command.
     *
     * @param input User input string.
     * @return Command corresponding to the user input.
     * @throws GoofyException If the command is invalid or input is malformed.
     */
    public Command parseCommand(String input) throws GoofyException {
        if (input.equalsIgnoreCase("bye")) {
            return new ExitCommand();
        } else if (input.equalsIgnoreCase("list")) {
            return new ListCommand();
        } else if (input.equalsIgnoreCase("mark") || input.startsWith("mark ")) {
            return parseMark(input);
        } else if (input.equalsIgnoreCase("unmark") || input.startsWith("unmark ")) {
            return parseUnmark(input);
        } else if (input.equalsIgnoreCase("delete") || input.startsWith("delete ")) {
            return parseDelete(input);
        } else if (input.equalsIgnoreCase("todo") || input.startsWith("todo ")) {
            return parseTodo(input);
        } else if (input.equalsIgnoreCase("deadline") || input.startsWith("deadline ")) {
            return parseDeadline(input);
        } else if (input.equalsIgnoreCase("event") || input.startsWith("event ")) {
            return parseEvent(input);
        } else {
            throw new GoofyException("\"" + input + "\"?? A-hyuck, I have NO idea what that means! "
                    + "Try one of these instead: todo, deadline, event, list, mark, unmark, "
                    + "delete, bye.");
        }
    }

    /**
     * Parses a todo command and returns the corresponding command.
     *
     * @param input User input string.
     * @return AddTodoCommand with the parsed description.
     * @throws GoofyException If the todo description is empty.
     */
    private Command parseTodo(String input) throws GoofyException {
        if (input.trim().equalsIgnoreCase("todo")) {
            throw new GoofyException("Gawrsh, a todo with no name? Even I'm not that forgetful! "
                    + "Usage: todo <description>");
        }
        String description = input.substring(TODO_COMMAND_LENGTH).trim();
        if (description.isEmpty()) {
            throw new GoofyException("Gawrsh, a todo with no name? Even I'm not that forgetful! "
                    + "Usage: todo <description>");
        }
        return new AddTodoCommand(description);
    }

    /**
     * Parses a deadline command and returns the corresponding command.
     *
     * @param input User input string.
     * @return AddDeadlineCommand with the parsed description and deadline.
     * @throws GoofyException If the format is invalid or fields are empty.
     */
    private Command parseDeadline(String input) throws GoofyException {
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
        return new AddDeadlineCommand(description, by);
    }

    /**
     * Parses an event command and returns the corresponding command.
     *
     * @param input User input string.
     * @return AddEventCommand with the parsed description and time range.
     * @throws GoofyException If the format is invalid or fields are empty.
     */
    private Command parseEvent(String input) throws GoofyException {
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
        return new AddEventCommand(description, from, to);
    }

    /**
     * Parses a mark command and returns the corresponding command.
     *
     * @param input User input string.
     * @return MarkCommand with the parsed task number.
     * @throws GoofyException If the task number is missing or not a valid integer.
     */
    private Command parseMark(String input) throws GoofyException {
        if (input.trim().equalsIgnoreCase("mark")) {
            throw new GoofyException("Mark WHAT exactly?! Give me a number! "
                    + "Usage: mark <task number>");
        }
        try {
            int taskNumber = Integer.parseInt(input.substring(MARK_COMMAND_LENGTH).trim());
            return new MarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new GoofyException("A-hyuck, that doesn't look like a number to me! "
                    + "Usage: mark <task number>");
        }
    }

    /**
     * Parses an unmark command and returns the corresponding command.
     *
     * @param input User input string.
     * @return UnmarkCommand with the parsed task number.
     * @throws GoofyException If the task number is missing or not a valid integer.
     */
    private Command parseUnmark(String input) throws GoofyException {
        if (input.trim().equalsIgnoreCase("unmark")) {
            throw new GoofyException("Unmark WHAT exactly?! Give me a number! "
                    + "Usage: unmark <task number>");
        }
        try {
            int taskNumber = Integer.parseInt(input.substring(UNMARK_COMMAND_LENGTH).trim());
            return new UnmarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new GoofyException("A-hyuck, that doesn't look like a number to me! "
                    + "Usage: unmark <task number>");
        }
    }

    /**
     * Parses a delete command and returns the corresponding command.
     *
     * @param input User input string.
     * @return DeleteCommand with the parsed task number.
     * @throws GoofyException If the task number is missing or not a valid integer.
     */
    private Command parseDelete(String input) throws GoofyException {
        if (input.trim().equalsIgnoreCase("delete")) {
            throw new GoofyException("Delete WHAT exactly?! Give me a number! "
                    + "Usage: delete <task number>");
        }
        try {
            int taskNumber = Integer.parseInt(input.substring(DELETE_COMMAND_LENGTH).trim());
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new GoofyException("A-hyuck, that doesn't look like a number to me! "
                    + "Usage: delete <task number>");
        }
    }
}