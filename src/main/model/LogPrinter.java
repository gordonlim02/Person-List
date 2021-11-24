package model;

public class LogPrinter {
    public LogPrinter() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }
}
