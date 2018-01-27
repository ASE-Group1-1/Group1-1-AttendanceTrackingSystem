package de.ase11.attendanceTrackingSystem.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Holiday {
    private Date holidayStart;
    private Date holidayEnd;

    public Holiday (Date start, Date end){
        this.holidayStart = start;
        this.holidayEnd = end;
    }

    public int getHolidayDuration() {
        int duration;

        int weekStart = this.getWeek(this.holidayStart);
        int weekEnd = this.getWeek(this.holidayEnd);

        if(weekStart > weekEnd){
            duration = 52 - weekStart + weekEnd + 1;
        } else {
            duration = weekEnd - weekStart + 1;
        }

        return duration;
    }

    public boolean isHolidayInThePast(int currentWeek) {
        return currentWeek > this.getWeek(this.holidayEnd);
    }

    public boolean onHoliday(int currentWeek) {
        return this.getWeek(this.holidayStart) <= currentWeek && currentWeek <= this.getWeek(this.holidayEnd);
    }

    private int getWeek(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return week;
    }
}
