package textEditor;

import static java.lang.System.exit;
import java.time.LocalDateTime;
import java.util.*;

class Action {
    private int id;
    private LocalDateTime timestamp;
    private boolean isInsert;
    private int lineNumber;
    private String text;

    public Action(int id, LocalDateTime timestamp, boolean isInsert, int lineNumber, String text) {
        this.id = id;
        this.timestamp = timestamp;
        this.isInsert = isInsert;
        this.lineNumber = lineNumber;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isInsert() {
        return isInsert;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getText() {
        return text;
    }
}

class Function {
    private String[] notepad;
    private int capacity;
    private Stack<Action> undoActions;
    private Stack<Action> redoActions;
    private Queue<String> clipBoard;
    private int id;

    public Function(int capacity) {
        this.capacity = capacity;
        this.notepad = new String[capacity];
        Arrays.fill(this.notepad, "");
        this.undoActions = new Stack<>();
        this.redoActions = new Stack<>();
        this.clipBoard = new LinkedList<>();
        this.id = 0;
    }

    public void display() {
        Arrays.stream(notepad).forEach(System.out::println);
    }

    public void display(int n, int m) {
        if (m > capacity || n > m) {
            throw new RuntimeException("Unable to display! Please check the constraints or the input");
        }
        for (int i = n - 1; i < m; ++i) {
            System.out.println(notepad[i]);
        }
    }

    public void insertLine(int n, String text) {
        if (n > capacity) {
            throw new RuntimeException("Notepad limit crossed!");
        }
        notepad[n - 1] = text;
        undoActions.push(new Action(id++, LocalDateTime.now(), true, n, text));
    }

    public void delete(int n) {
        if (n > capacity) {
            throw new RuntimeException("There is no such row to delete!");
        }
        if (Objects.nonNull(notepad[n]) && !notepad[n - 1].isEmpty()) {
            Action action = new Action(id++, LocalDateTime.now(), false, n, notepad[n - 1]);
            redoActions.push(action);
            notepad[n - 1] = "";
        }
        display();
    }

    public void delete(int n, int m) {
        if (n > m) {
            throw new RuntimeException("Unable to delete! Please check the constraints or the input");
        }
        for (int i = n; i < m; ++i) {
            delete(i);
        }
        display();
    }

    public void copy(int n, int m) {
        if (m > capacity || n > m) {
            throw new RuntimeException("Unable to copy! Out of bounds");
        }
        StringBuilder copyText = new StringBuilder();
        for (int i = n; i < m; ++i) {
            copyText.append(notepad[i]);
        }
        if (copyText.length() > 0) {
            clipBoard.add(copyText.toString());
        }
    }

    public void paste(int n) {
        if (clipBoard.isEmpty()) {
            throw new RuntimeException("Nothing to paste!");
        }
        String text = clipBoard.peek();
        insertLine(n, text);
        display();
    }

    public void undo() {
        if (undoActions.isEmpty()) {
            throw new RuntimeException("Nothing to Undo");
        }
        Action action = undoActions.peek();
        undoActions.pop();
        delete(action.getLineNumber());
        display();
    }

    public void redo() {
        if (redoActions.isEmpty()) {
            throw new RuntimeException("Nothing to Redo");
        }
        Action action = redoActions.peek();
        redoActions.pop();
        insertLine(action.getLineNumber(), action.getText());
        display();
    }
}

public class textEditor {
    public static void main(String[] args) {
        // Create a text editor with a capacity of 10 lines
        Function textEditor = new Function(10);

        // Insert some lines
        textEditor.insertLine(1, "Line 1");
        textEditor.insertLine(2, "Line 2");
        textEditor.insertLine(3, "Line 3");

        // Display the content of the text editor
        System.out.println("Current Content:");
        textEditor.display();

        // Delete a line
        textEditor.delete(2);

        // Display the content after deletion
        System.out.println("\nContent after deletion:");
        textEditor.display();

        // Copy lines 1 to 2
        textEditor.copy(1, 3);

        // Paste the copied lines after line 1
        textEditor.paste(1);

        // Display the content after pasting
        System.out.println("\nContent after pasting:");
        textEditor.display();

        // Undo the last action (pasting)
        textEditor.undo();

        // Display the content after undoing
        System.out.println("\nContent after undoing:");
        textEditor.display();
    }
}