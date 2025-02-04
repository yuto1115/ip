package odin.storage;

import odin.exception.WrongFormatException;
import odin.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String SEPARATOR = "_________________________________________________________________________________________________________";
    private final String recordFilePath;

    public Storage(String recordFilePath) {
        this.recordFilePath = recordFilePath;
    }

    public TaskList load() {
        File file = new File(this.recordFilePath);
        try (Scanner fileScanner = new Scanner(file)) {
            TaskList taskList = new TaskList();
            ArrayList<String> currentRecord = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.equals(SEPARATOR)) {
                    taskList.addFromTaskRecord(currentRecord);
                    currentRecord.clear();
                } else {
                    currentRecord.add(line);
                }
            }
            if (!currentRecord.isEmpty()) {
                throw new WrongFormatException("Task record must end with a separator line.");
            }
            fileScanner.close();
            System.out.println(String.format("(system) Successfully restored the task list from record file %s.", recordFilePath));
            System.out.println(SEPARATOR);
            return taskList;
        } catch (FileNotFoundException e) {
            System.out.println("(system) Record file not found. Initialising with an empty task list.");
        } catch (WrongFormatException e) {
            System.out.println("(system) " + e.getMessage());
            System.out.println("(system) Task record is broken. Initialising with an empty task list.");
        }
        System.out.println(SEPARATOR);
        return new TaskList();
    }

    public void save(TaskList taskList) {
        File file = new File(this.recordFilePath);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        ArrayList<ArrayList<String>> taskRecordList = taskList.getTaskRecordList();
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            for (ArrayList<String> taskRecord : taskRecordList) {
                for (String s : taskRecord) {
                    fileWriter.write(s + "\n");
                }
                fileWriter.write(SEPARATOR + "\n");
            }
            fileWriter.close();
            System.out.println(String.format("(system) Successfully saved the task list to record file %s.", recordFilePath));
        } catch (IOException e) {
            System.out.println("(system) An error occurred while saving the task list.\n" + e.getMessage());
        }
    }
}
