package odin.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import odin.exception.WrongFormatException;
import odin.task.Task;
import odin.task.TaskList;

public class ParserTest {
    /**
     * Stub TaskList initially containing only one stub task, which does not allow to add new tasks
     */
    private static class TaskListStub extends TaskList {
        protected int size = 1;

        @Override
        public int getSize() {
            return size;
        }

        @Override
        public void add(Task task) {
            fail();
        }

        @Override
        public void addFromTaskRecord(ArrayList<String> taskRecord) {
            fail();
        }

        @Override
        public void markAsDone(int idx) {
            assertEquals(0, idx);
        }

        @Override
        public void markAsNotDone(int idx) {
            assertEquals(0, idx);
        }

        @Override
        public void delete(int idx) {
            assertEquals(0, idx);
            size -= 1;
        }

        @Override
        public String getTaskDescription(int idx) {
            assertEquals(0, idx);
            return "stub";
        }

        @Override
        public String getTaskName(int idx) {
            assertEquals(0, idx);
            return "read book";
        }

        @Override
        public ArrayList<ArrayList<String>> getTaskRecordList() {
            fail();
            return new ArrayList<>();
        }
    }

    /**
     * Stub class for TaskList containing only one task, which allows to add only to-do task
     */
    private static class TaskListStubTodo extends TaskListStub {
        @Override
        public void add(Task task) {
            assertEquals("[T][ ] read book", task.toString());
            size += 1;
        }

        @Override
        public String getTaskDescription(int idx) {
            assertEquals(1, idx);
            return "todo stub";
        }
    }

    /**
     * Stub class for TaskList containing only one task, which allows to add only deadline task
     */
    private static class TaskListStubDeadline extends TaskListStub {
        @Override
        public void add(Task task) {
            assertEquals("[D][ ] read book (by: Jan 1 2030, 12:00)", task.toString());
            size += 1;
        }

        @Override
        public String getTaskDescription(int idx) {
            assertEquals(1, idx);
            return "deadline stub";
        }
    }

    /**
     * Stub class for TaskList containing only one task, which allows to add only event task
     */
    private static class TaskListStubEvent extends TaskListStub {
        @Override
        public void add(Task task) {
            assertEquals("[E][ ] read book (from: Jan 1 2030, 12:00 to: Jan 1 2040, 12:00)", task.toString());
            size += 1;
        }

        @Override
        public String getTaskDescription(int idx) {
            assertEquals(1, idx);
            return "event stub";
        }
    }

    @Test
    public void parseAndHandle_exitCommand() {
        // ok
        try {
            ArrayList<String> tokens = new ArrayList<>(List.of("exit"));
            TaskList taskList = new TaskListStub();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(true, p.getKey());
            assertEquals(new ArrayList<>(), p.getValue());
        } catch (WrongFormatException e) {
            fail();
        }

        // bad
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("exit", "read", "book"));
            TaskList taskList = new TaskListStub();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("'exit' command should not have additional tokens.", e.getMessage());
        }
    }

    @Test
    public void parseAndHandle_listCommand() {
        // ok
        try {
            ArrayList<String> tokens = new ArrayList<>(List.of("list"));
            TaskList taskList = new TaskListStub();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(false, p.getKey());
            assertEquals(new ArrayList<>(Arrays.asList("These are the tasks upon the list.", "1. stub")), p.getValue());
        } catch (WrongFormatException e) {
            fail();
        }

        // bad
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("list", "read", "book"));
            TaskList taskList = new TaskListStub();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("'list' command should not have additional tokens.", e.getMessage());
        }
    }

    @Test
    public void parseAndHandle_findCommand() {
        // ok, found
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("find", "book"));
            TaskList taskList = new TaskListStub();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(false, p.getKey());
            assertEquals(new ArrayList<>(Arrays.asList(
                    "These are the tasks that contain the keyword(s) 'book'.", "1. stub")), p.getValue());
        } catch (WrongFormatException e) {
            fail();
        }

        // ok, found 2
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("find", "read", "boo"));
            TaskList taskList = new TaskListStub();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(false, p.getKey());
            assertEquals(new ArrayList<>(Arrays.asList(
                    "These are the tasks that contain the keyword(s) 'read boo'.", "1. stub")), p.getValue());
        } catch (WrongFormatException e) {
            fail();
        }

        // ok, not found
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("find", "real", "book"));
            TaskList taskList = new TaskListStub();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(false, p.getKey());
            assertEquals(new ArrayList<>(List.of(
                    "There are no tasks that contain the keyword(s) 'real book'.")), p.getValue());
        } catch (WrongFormatException e) {
            fail();
        }

        // bad
        try {
            ArrayList<String> tokens = new ArrayList<>(List.of("find"));
            TaskList taskList = new TaskListStub();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("KEYWORDS cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void parseAndHandle_markCommand() {
        // ok
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("mark", "1"));
            TaskList taskList = new TaskListStub();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(false, p.getKey());
            assertEquals(new ArrayList<>(Arrays.asList(
                    "Task 1 has been marked as completed. May the next task be approached with equal diligence.",
                    "  stub")),
                    p.getValue());
        } catch (WrongFormatException e) {
            fail();
        }

        // bad
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("mark", "read", "book"));
            TaskList taskList = new TaskListStub();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("'mark' command must be followed by an integer.", e.getMessage());
        }

        // bad 2
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("mark", "2"));
            TaskList taskList = new TaskListStub();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("The task index you speak of is incorrect. There are tasks numbered 1 through 1.",
                    e.getMessage());
        }
    }

    @Test
    public void parseAndHandle_unmarkCommand() {
        // ok
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("unmark", "1"));
            TaskList taskList = new TaskListStub();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(false, p.getKey());
            assertEquals(new ArrayList<>(Arrays.asList(
                    "Task 1 remains unfinished. Let it be revisited with renewed focus and determination.", "  stub")),
                    p.getValue());
        } catch (WrongFormatException e) {
            fail();
        }

        // bad
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("unmark", "read", "book"));
            TaskList taskList = new TaskListStub();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("'unmark' command must be followed by an integer.", e.getMessage());
        }

        // bad 2
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("unmark", "2"));
            TaskList taskList = new TaskListStub();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("The task index you speak of is incorrect. There are tasks numbered 1 through 1.",
                    e.getMessage());
        }
    }

    @Test
    public void parseAndHandle_deleteCommand() {
        // ok
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("delete", "1"));
            TaskList taskList = new TaskListStub();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(false, p.getKey());
            assertEquals(new ArrayList<>(Arrays.asList("This task has been removed from the list.", "  stub",
                    "Now, 0 tasks stand before you. Choose wisely, for time is ever fleeting.")),
                    p.getValue());
        } catch (WrongFormatException e) {
            fail();
        }

        // bad
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("delete", "read", "book"));
            TaskList taskList = new TaskListStub();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("'delete' command must be followed by an integer.", e.getMessage());
        }

        // bad 2
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("delete", "2"));
            TaskList taskList = new TaskListStub();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("The task index you speak of is incorrect. There are tasks numbered 1 through 1.",
                    e.getMessage());
        }
    }

    @Test
    public void parseAndHandle_addTodoCommand() {
        // ok
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("todo", "read", "book"));
            TaskList taskList = new TaskListStubTodo();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(false, p.getKey());
            assertEquals(new ArrayList<>(Arrays.asList("This task has been added to the list.",
                    "  todo stub", "Now, 2 tasks stand before you. Choose wisely, for time is ever fleeting.")),
                    p.getValue());
        } catch (WrongFormatException e) {
            fail();
        }

        // bad
        try {
            ArrayList<String> tokens = new ArrayList<>(List.of("todo"));
            TaskList taskList = new TaskListStubTodo();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("TASK cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void parseAndHandle_addDeadlineCommand() {
        // ok
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList(
                    "deadline", "read", "book", "/by", "2030-01-01", "12:00"));
            TaskList taskList = new TaskListStubDeadline();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(false, p.getKey());
            assertEquals(new ArrayList<>(Arrays.asList("This task has been added to the list.",
                    "  deadline stub", "Now, 2 tasks stand before you. Choose wisely, for time is ever fleeting.")),
                    p.getValue());
        } catch (WrongFormatException e) {
            fail();
        }

        // bad
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList(
                    "deadline", "read", "book", "2030-01-01", "12:00"));
            TaskList taskList = new TaskListStubDeadline();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("'/by not found.", e.getMessage());
        }

        // bad 2
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("deadline", "/by", "2030-01-01", "12:00"));
            TaskList taskList = new TaskListStubDeadline();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("TASK cannot be empty.", e.getMessage());
        }

        // bad 3
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList("deadline", "read", "book", "/by"));
            TaskList taskList = new TaskListStubDeadline();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("DATE cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void parseAndHandle_addEventCommand() {
        // ok
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList(
                    "event", "read", "book", "/from", "2030-01-01", "12:00", "/to", "2040-01-01", "12:00"));
            TaskList taskList = new TaskListStubEvent();
            Pair<Boolean, ArrayList<String>> p = new Parser().parseAndHandle(tokens, taskList);
            assertEquals(false, p.getKey());
            assertEquals(new ArrayList<>(Arrays.asList(
                    "This task has been added to the list.",
                    "  event stub", "Now, 2 tasks stand before you. Choose wisely, for time is ever fleeting.")),
                    p.getValue());
        } catch (WrongFormatException e) {
            System.out.println(e.getMessage());
            fail();
        }

        // bad
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList(
                    "event", "read", "book", "2030-01-01", "12:00", "/to", "2040-01-01", "12:00"));
            TaskList taskList = new TaskListStubEvent();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("/from not found.", e.getMessage());
        }

        // bad 2
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList(
                    "event", "read", "book", "/from", "2030-01-01", "12:00", "2040-01-01", "12:00"));
            TaskList taskList = new TaskListStubEvent();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("/to not found.", e.getMessage());
        }

        // bad 3
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList(
                    "event", "read", "book", "/to", "2030-01-01", "12:00", "/from", "2040-01-01", "12:00"));
            TaskList taskList = new TaskListStubEvent();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("/from must come before /to.", e.getMessage());
        }

        // bad 4
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList(
                    "event", "/from", "2030-01-01", "12:00", "/to", "2040-01-01", "12:00"));
            TaskList taskList = new TaskListStubEvent();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("TASK cannot be empty.", e.getMessage());
        }

        // bad 5
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList(
                    "event", "read", "book", "/from", "/to", "2040-01-01", "12:00"));
            TaskList taskList = new TaskListStubEvent();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("DATE cannot be empty.", e.getMessage());
        }

        // bad 6
        try {
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList(
                    "event", "read", "book", "/from", "2030-01-01", "12:00", "/to"));
            TaskList taskList = new TaskListStubEvent();
            new Parser().parseAndHandle(tokens, taskList);
            fail();
        } catch (WrongFormatException e) {
            assertEquals("DATE cannot be empty.", e.getMessage());
        }
    }
}
