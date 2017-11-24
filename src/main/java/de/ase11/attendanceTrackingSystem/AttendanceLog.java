package de.ase11.attendanceTrackingSystem;

import com.googlecode.objectify.ObjectifyService;
import de.ase11.attendanceTrackingSystem.model.Attendance;

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

        List<Attendance> attendancesTmp = ObjectifyService.ofy().load().type(Attendance.class).list();
        for (Attendance attendance : attendancesTmp) {
            attendanceLog.attendances.add(attendance);
        }

        return attendanceLog;
    }

    public Long getFilterGroupId() {
        return filterGroupId;
    }

}
