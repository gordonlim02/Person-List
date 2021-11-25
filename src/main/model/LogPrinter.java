package model;

// A logPrinter that prints the events on the event log to the console
public class LogPrinter {

    // EFFECTS: prints all events in EventLog onto the console
    public LogPrinter() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }
}
