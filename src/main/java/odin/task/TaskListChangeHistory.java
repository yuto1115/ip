package odin.task;

/**
 * Class to represent a change of the task list.
 */
public class TaskListChangeHistory {
    enum ChangeType {
        ADD, DELETE, MARK, UNMARK
    }

    private final ChangeType changeType;
    private final Task task;
    private final int idx;

    private TaskListChangeHistory(ChangeType changeType, Task task, int idx) {
        this.changeType = changeType;
        this.task = task;
        this.idx = idx;
    }

    /**
     * Gets a history for addition of a task.
     *
     * @param addedTaskIndex Index in the task list of the added task.
     */
    public static TaskListChangeHistory getHistoryForAdd(int addedTaskIndex) {
        return new TaskListChangeHistory(ChangeType.ADD, null, addedTaskIndex);
    }

    /**
     * Gets a history for deletion of a task.
     *
     * @param deletedTask      The deleted task.
     * @param deletedTaskIndex Index in the task list of the deleted task before the deletion.
     */
    public static TaskListChangeHistory getHistoryForDelete(Task deletedTask,
                                                            int deletedTaskIndex) {
        return new TaskListChangeHistory(ChangeType.DELETE, deletedTask, deletedTaskIndex);
    }

    /**
     * Gets a history for marking a task.
     *
     * @param markedTaskIndex Index in the task list of the marked task.
     */
    public static TaskListChangeHistory getHistoryForMark(int markedTaskIndex) {
        return new TaskListChangeHistory(ChangeType.MARK, null, markedTaskIndex);
    }

    /**
     * Gets a history for unmarking a task.
     *
     * @param unmarkedTaskIndex Index in the task list of the unmarked task.
     */
    public static TaskListChangeHistory getHistoryForUnmark(int unmarkedTaskIndex) {
        return new TaskListChangeHistory(ChangeType.UNMARK, null, unmarkedTaskIndex);
    }

    /**
     * Reverts a change, represented by the given change history, of the given task list.
     *
     * @return A system message to describe the revert operation.
     */
    public static String revertChange(TaskList taskList,
                                      TaskListChangeHistory changeHistory) {
        if (changeHistory.changeType == ChangeType.ADD) {
            String str = taskList.getTaskDescription(changeHistory.idx);
            taskList.delete(changeHistory.idx);
            return String.format("Deleted the following task:\n  %s", str);
        } else if (changeHistory.changeType == ChangeType.DELETE) {
            assert changeHistory.task != null;
            taskList.add(changeHistory.idx, changeHistory.task);
            return String.format("Added the following task:\n  %s", changeHistory.task);
        } else if (changeHistory.changeType == ChangeType.MARK) {
            taskList.markAsNotDone(changeHistory.idx);
            String str = taskList.getTaskDescription(changeHistory.idx);
            return String.format("Unmarked the following task:\n  %s", str);
        } else if (changeHistory.changeType == ChangeType.UNMARK) {
            taskList.markAsDone(changeHistory.idx);
            String str = taskList.getTaskDescription(changeHistory.idx);
            return String.format("Marked the following task:\n  %s", str);
        } else {
            throw new RuntimeException("Unknown change type: " + changeHistory.changeType);
        }
    }
}
