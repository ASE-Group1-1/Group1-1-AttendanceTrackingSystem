package de.ase11.attendanceTrackingSystem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "attendanceLog")
// If you want you can define the order in which the fields are written
// Optional
@XmlType(propOrder = { "attendances" , "filterGroupId"})
public class AttendanceLog {
    @XmlElementWrapper(name = "attendances")
    @XmlElement(name = "attendance")
    private List<Attendance> attendances;
    @XmlElement(name = "filterGroupId")
    private Long filterGroupId;

    public AttendanceLog() {}

    public static AttendanceLog createAttendanceLog() {
        AttendanceLog attendanceLog = new AttendanceLog();
        attendanceLog.attendances = new ArrayList<>();

        //Create dummy Attendances
        //TODO: retrieve attendances from storage (maybe first from Objectify later from Notary)
        Attendance attendance1 = new Attendance();
        Attendance attendance2 = new Attendance();
        attendance1.setPresented(true);
        attendance2.setPresented(false);

        attendanceLog.attendances.add(attendance1);
        attendanceLog.attendances.add(attendance2);

        return attendanceLog;
    }

    public Long getFilterGroupId() {
        return filterGroupId;
    }

}
