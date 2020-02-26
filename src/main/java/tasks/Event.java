package tasks;

/**
 * tasks that need to be done before a specific date/time
 * e.g., submit report by 11/10/2019 5pm
 */
public class Event extends Task {
    protected String dateStr;

    public Event(String taskName, String dateStr) {
        super(taskName);
        this.category = 'E';
        this.dateStr = dateStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public String toString() {
        return (super.toString() + " (at: " + dateStr +")" );
    }
}
