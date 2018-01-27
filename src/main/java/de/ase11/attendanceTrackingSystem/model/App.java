package de.ase11.attendanceTrackingSystem.model;

import com.google.appengine.api.users.User;

import java.util.*;

public class App {
    private Date semesterStart;
    private Date semesterEnd;
    private List<Holiday> holidayList;
    private User appOwner;

    public App(Date semesterStart, Date semesterEnd, List<Holiday> holidayList, User appOwner) {
        this.semesterStart = semesterStart;
        this.semesterEnd = semesterEnd;
        this.holidayList = holidayList;
        this.appOwner = appOwner;
    }

    public App() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(2017,9,29);
        this.semesterStart = calendar.getTime();
        calendar.set(2018,1,10);
        this.semesterEnd = calendar.getTime();

        this.holidayList = new ArrayList<>();

        calendar.set(2017,11,24);
        Date holidayStart = calendar.getTime();
        calendar.set(2018,0,6);
        Date holidayEnd = calendar.getTime();


        Holiday holiday = new Holiday(holidayStart,holidayEnd);
        this.holidayList.add(holiday);
    }

    public int getSemesterDuration() {
        int duration;

        int weekStart = this.getWeek(this.semesterStart);
        int weekEnd = this.getWeek(this.semesterEnd);

        duration = this.calculateDurationInWeeks(weekStart,weekEnd);

        for (Holiday holiday : holidayList) {
            duration = duration - holiday.getHolidayDuration();
        }

        return duration;
    }

    public int getCurrentWeek() {
        int week;

        int weekStart = this.getWeek(this.semesterStart);
        int weekNow = this.getWeek(new Date());

        week = this.calculateDurationInWeeks(weekStart,weekNow);

        for (Holiday holiday : holidayList) {
            if(holiday.onHoliday(weekNow)) {
                throw new RuntimeException();
            } else if(holiday.isHolidayInThePast(weekNow)) {
                week = week - holiday.getHolidayDuration();
            }
        }

        return week;
    }

    private int getWeek(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    private int calculateDurationInWeeks(int startCalendarWeek, int endCalendarWeek) {
        int duration;

        if(startCalendarWeek > endCalendarWeek) {
            duration = 52 - startCalendarWeek + endCalendarWeek + 1;
        } else {
            duration = endCalendarWeek - startCalendarWeek + 1;
        }

        return duration;
    }

}
