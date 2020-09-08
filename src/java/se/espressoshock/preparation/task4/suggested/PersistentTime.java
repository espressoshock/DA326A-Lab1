package se.espressoshock.preparation.task4.suggested;

import java.util.Date;
import java.util.Calendar;

public class PersistentTime {
    private Date time;

    public PersistentTime() {
        time = Calendar.getInstance().getTime();
    }

    public Date getTime() {
        return time;
    }
}
