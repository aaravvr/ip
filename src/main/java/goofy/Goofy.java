package goofy;

import goofy.command.Command;
import goofy.exception.GoofyException;
import goofy.storage.Storage;
import goofy.ui.Parser;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents the main entry point for the Goofy chatbot application.
 * Goofy helps users manage their tasks via a command-line interface.
 */
public class Goofy {
    private final Ui ui;
    private final TaskList tasks;
    private final Parser parser;
    private final Storage storage;

    /**
     * Creates a new Goofy application instance.
     */
    public Goofy() {
        this.ui = new Ui();
        this.storage = new Storage();
        this.parser = new Parser();

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.loadTasks());
        } catch (GoofyException e) {
            ui.showError(e.getMessage());
            loadedTasks = new TaskList();
        }
        this.tasks = loadedTasks;
    }

    /**
     * Runs the Goofy application.
     */
    public void run() {
        ui.showWelcome();
        runCommandLoop();
    }

    /**
     * Runs the main command loop that reads and processes user input.
     */
    private void runCommandLoop() {
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                Command command = parser.parseCommand(input);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (GoofyException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.closeScanner();
    }

    /**
     * Starts the Goofy chatbot application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Goofy().run();
    }
}