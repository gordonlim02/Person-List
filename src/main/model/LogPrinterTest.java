package model;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogPrinterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    void testLogPrinter() {
        System.setOut(new PrintStream(outContent));
        PersonList personList = new PersonList();
        personList.addPerson(new Person("John", "Male", "Brown", "ICICS Building"));
        LogPrinter logPrinter = new LogPrinter();
        assertTrue(outContent.toString().contains("Added a person named John"));
    }
}
