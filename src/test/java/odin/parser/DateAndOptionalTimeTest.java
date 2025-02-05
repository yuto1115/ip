package odin.parser;

import odin.exception.WrongFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;


public class DateAndOptionalTimeTest {
    @Test
    public void DateAndOptionalTime_wrongFormat_exceptionThrown() {
        // 0 tokens
        try {
            new DateAndOptionalTime(new ArrayList<>());
            fail();
        } catch (WrongFormatException e) {
            assertEquals("Date & time must contain one or two tokens.", e.getMessage());
        }

        // 3 tokens
        try {
            new DateAndOptionalTime(new ArrayList<>(Arrays.asList("2025-01-01", "00:00", "11:00")));
            fail();
        } catch (WrongFormatException e) {
            assertEquals("Date & time must contain one or two tokens.", e.getMessage());
        }

        // No date
        try {
            new DateAndOptionalTime(new ArrayList<>(Arrays.asList("08:00")));
            fail();
        } catch (WrongFormatException e) {
            assertEquals("Date cannot be parsed as yyyy-MM-dd.", e.getMessage());
        }

        // Date and time swapped
        try {
            new DateAndOptionalTime(new ArrayList<>(Arrays.asList("08:00", "2025-01-01")));
            fail();
        } catch (WrongFormatException e) {
            assertEquals("Date cannot be parsed as yyyy-MM-dd.", e.getMessage());
        }

        // Wrong date format
        try {
            new DateAndOptionalTime(new ArrayList<>(Arrays.asList("Jan 1 2025", "08:00")));
            fail();
        } catch (WrongFormatException e) {
            assertEquals("Date cannot be parsed as yyyy-MM-dd.", e.getMessage());
        }

        // Wrong date format 2
        try {
            new DateAndOptionalTime(new ArrayList<>(Arrays.asList("2025-1-1", "08:00")));
            fail();
        } catch (WrongFormatException e) {
            assertEquals("Date cannot be parsed as yyyy-MM-dd.", e.getMessage());
        }

        // Wrong time format
        try {
            new DateAndOptionalTime(new ArrayList<>(Arrays.asList("2025-01-01", "8pm")));
            fail();
        } catch (WrongFormatException e) {
            assertEquals("Time cannot be parsed as hh:mm or hh:mm:ss.", e.getMessage());
        }

        // Wrong time format 2
        try {
            new DateAndOptionalTime(new ArrayList<>(Arrays.asList("2025-01-01", "8:23")));
            fail();
        } catch (WrongFormatException e) {
            assertEquals("Time cannot be parsed as hh:mm or hh:mm:ss.", e.getMessage());
        }
    }

    @Test
    public void testToString() {
        // Date only
        try {
            String str = new DateAndOptionalTime(new ArrayList<>(Arrays.asList("2025-01-01"))).toString();
            assertEquals("Jan 1 2025", str);
        } catch (WrongFormatException e) {
            fail();
        }

        // Date and time
        try {
            String str = new DateAndOptionalTime(new ArrayList<>(Arrays.asList("2025-12-10", "13:20"))).toString();
            assertEquals("Dec 10 2025, 13:20", str);
        } catch (WrongFormatException e) {
            fail();
        }

        // Date and time with seconds
        try {
            String str = new DateAndOptionalTime(new ArrayList<>(Arrays.asList("2025-12-10", "13:20:15"))).toString();
            assertEquals("Dec 10 2025, 13:20:15", str);
        } catch (WrongFormatException e) {
            fail();
        }
    }

    @Test
    public void testGetOriginalString() {
        // Date only
        try {
            String str = new DateAndOptionalTime(new ArrayList<>(Arrays.asList("2025-01-01"))).getOriginalString();
            assertEquals("2025-01-01", str);
        } catch (WrongFormatException e) {
            fail();
        }

        // Date and time
        try {
            String str = new DateAndOptionalTime(new ArrayList<>(Arrays.asList("2025-12-10", "13:20"))).getOriginalString();
            assertEquals("2025-12-10 13:20", str);
        } catch (WrongFormatException e) {
            fail();
        }

        // Date and time with seconds
        try {
            String str = new DateAndOptionalTime(new ArrayList<>(Arrays.asList("2025-12-10", "13:20:15"))).getOriginalString();
            assertEquals("2025-12-10 13:20:15", str);
        } catch (WrongFormatException e) {
            fail();
        }
    }
}
