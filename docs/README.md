# Goofy User Guide

Goofy is a **command-line chatbot** that helps you manage your tasks. If you can type fast, Goofy can manage your tasks faster than most GUI apps. A-hyuck!

---

## Quick Start

1. Ensure you have Java `17` or above installed.
1. Download the latest `goofy.jar` from the [releases page](https://github.com/aaravvr/ip/releases).
1. Copy the file to the folder you want to use as the home folder for Goofy.
1. Open a terminal in that folder and run:

```
java -jar goofy.jar
```

1. Type a command and press Enter to execute it.
1. Refer to the [Features](#features) section below for a list of all commands.

---

## Features

> **Notes about the command format:**
>
> * Words in `UPPER_CASE` are parameters to be supplied by you.
> * Parameters must be in the order shown.
> * Extraneous parameters for commands that do not take parameters (e.g., `list`, `bye`) will be treated as errors.

---

### Adding a todo task: `todo`

Adds a task with no date or time attached to it.

Format: `todo DESCRIPTION`

Example:

```
todo read book
```

Expected output:

```
    ________________________________________________________________________________________________________________________________________
    Okie dokie! I went ahead and scribbled that down:
      [T][ ] read book
    Ya got 1 task now. Don't let 'em pile up like my laundry!
    ________________________________________________________________________________________________________________________________________
```

---

### Adding a deadline task: `deadline`

Adds a task that needs to be done before a specific date.

Format: `deadline DESCRIPTION /by DATE`

* `DATE` can be in `yyyy-MM-dd` format (e.g., `2019-12-01`) for smart date display, or any plain text (e.g., `Sunday`).

Examples:

```
deadline return book /by 2019-06-06
deadline submit report /by Sunday
```

---

### Adding an event task: `event`

Adds a task that spans a start and end date.

Format: `event DESCRIPTION /from START /to END`

* `START` and `END` can be in `yyyy-MM-dd` format or any plain text.

Example:

```
event project meeting /from 2019-06-06 /to 2019-06-08
```

---

### Listing all tasks: `list`

Shows all tasks currently in the list.

Format: `list`

Expected output:

```
    ________________________________________________________________________________________________________________________________________
    Hold on, lemme squint at your list real hard...
    1. [T][ ] read book
    2. [D][ ] return book (by: Jun 06 2019)
    3. [E][ ] project meeting (from: Jun 06 2019 to: Jun 08 2019)
    ________________________________________________________________________________________________________________________________________
```

---

### Marking a task as done: `mark`

Marks the specified task as done.

Format: `mark TASK_NUMBER`

Example:

```
mark 1
```

---

### Marking a task as not done: `unmark`

Marks the specified task as not done.

Format: `unmark TASK_NUMBER`

Example:

```
unmark 1
```

---

### Deleting a task: `delete`

Removes the specified task from the list permanently.

Format: `delete TASK_NUMBER`

Example:

```
delete 2
```

---

### Finding tasks by keyword: `find`

Finds all tasks whose descriptions contain the given keyword. The search is case-insensitive.

Format: `find KEYWORD`

Example:

```
find book
```

Expected output:

```
    ________________________________________________________________________________________________________________________________________
    Here are the matching tasks in your list:
    1. [T][ ] read book
    2. [D][ ] return book (by: Jun 06 2019)
    ________________________________________________________________________________________________________________________________________
```

---

### Finding tasks on a date: `date`

Lists all deadlines due on the given date, and all events that fall on or within that date.

Format: `date DATE`

* `DATE` must be in `yyyy-MM-dd` format.

Example:

```
date 2019-06-07
```

Expected output:

```
    ________________________________________________________________________________________________________________________________________
    Here's what's happenin' on Jun 07 2019:
    1. [E][ ] project meeting (from: Jun 06 2019 to: Jun 08 2019)
    ________________________________________________________________________________________________________________________________________
```

---

### Exiting the program: `bye`

Exits the program.

Format: `bye`

---

## Data Storage

Goofy saves your tasks automatically to `./data/goofy.txt` after every command. The file is loaded when Goofy starts up. You do not need to save manually.

> **Note:** Do not edit `goofy.txt` manually. Corrupted lines will be silently skipped on load.

---

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| todo | `todo DESCRIPTION` | `todo read book` |
| deadline | `deadline DESCRIPTION /by DATE` | `deadline return book /by 2019-06-06` |
| event | `event DESCRIPTION /from START /to END` | `event meeting /from 2019-06-06 /to 2019-06-08` |
| list | `list` | `list` |
| mark | `mark TASK_NUMBER` | `mark 1` |
| unmark | `unmark TASK_NUMBER` | `unmark 1` |
| delete | `delete TASK_NUMBER` | `delete 2` |
| find | `find KEYWORD` | `find book` |
| date | `date DATE` | `date 2019-06-06` |
| bye | `bye` | `bye` |