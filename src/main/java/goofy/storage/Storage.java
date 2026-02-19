package goofy.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import goofy.exception.GoofyException;
import goofy.task.Deadline;
import goofy.task.Event;
import goofy.task.Task;
import goofy.task.Todo;

/**
 * Handles loading and saving of tasks to the hard disk.
 * Responsible for file I/O operations and data persistence.
 */
public class Storage {
    private static final String DEFAULT_FILE_PATH = "./data/goofy.txt";
    private final String filePath;

    /**
     * Creates a new Storage instance with the default file path.
     */
    public Storage() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Creates a new Storage instance with the specified file path.
     *
     * @param filePath Path to the data file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the data file.
     * Creates the file and directory if they don't exist.
     *
     * @return ArrayList of tasks loaded from the file.
     * @throws GoofyException If there is an error reading or creating the file.
     */
    public ArrayList<Task> loadTasks() throws GoofyException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                createDataFile();
            } catch (IOException e) {
                throw new GoofyException("Gawrsh! I can't create the data file! " + e.getMessage());
            }
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTaskFromFile(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new GoofyException("Gawrsh! I can't read the file! " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves tasks to the data file.
     *
     * @param tasks ArrayList of tasks to save.
     * @throws GoofyException If there is an error writing to the file.
     */
    public void saveTasks(ArrayList<Task> tasks) throws GoofyException {
        try {
            createDataFile();
            try (FileWriter writer = new FileWriter(filePath)) {
                for (Task task : tasks) {
                    writer.write(convertTaskToFileFormat(task) + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new GoofyException("Gawrsh! I can't save the file! " + e.getMessage());
        }
    }

    /**
     * Creates the data file and its parent directories if they don't exist.
     *
     * @throws IOException If there is an error creating the file or directories.
     */
    private void createDataFile() throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * Converts a task to the file storage format.
     * Format: TYPE | DONE | DESCRIPTION | ADDITIONAL_INFO
     *
     * @param task Task to convert.
     * @return String representation for file storage.
     */
    private String convertTaskToFileFormat(Task task) {
        String isDone = task.getStatusIcon().equals("X") ? "1" : "0";
        String description = task.getDescription();

        if (task instanceof Todo) {
            return "T | " + isDone + " | " + description;
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + isDone + " | " + description + " | " + deadline.getBy();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + isDone + " | " + description + " | " + event.getFrom() + " | "
                    + event.getTo();
        }
        return "";
    }

    /**
     * Parses a line from the file and creates a task.
     *
     * @param line Line from the file.
     * @return Task parsed from the line, or null if parsing fails.
     */
    private Task parseTaskFromFile(String line) {
        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                return null;
            }

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task = null;
            switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length >= 4) {
                    task = new Deadline(description, parts[3]);
                }
                break;
            case "E":
                if (parts.length >= 5) {
                    task = new Event(description, parts[3], parts[4]);
                }
                break;
            default:
                return null;
            }

            if (task != null && isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            return null;
        }
    }
}