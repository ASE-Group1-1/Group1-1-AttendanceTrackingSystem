package de.ase11.attendanceTrackingSystem;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.List;

@XmlRootElement(name = "attendance")
@Entity
public class Attendance {
    @XmlElement(name = "id")
    @Id private Long id;
    @XmlElement(name = "studentId")
    private String studentId;
    @XmlElement(name = "groupId")
    private Long groupId;
    @XmlElement(name = "weekId")
    private int weekId;
    @XmlElement(name = "presented")
    private boolean presented;

    public Attendance() {};

    public Attendance(String studentId, Long groupId, int weekId) {
        this.studentId = studentId;
        this.groupId = groupId;
        this.weekId = weekId;
    }

    public Attendance(String studentId, Long groupId, int weekId, boolean presented) {
        this.studentId = studentId;
        this.groupId = groupId;
        this.weekId = weekId;
        this.presented = presented;
    }

    public static Attendance xmlToAttendance(String xml) {

        Attendance attendance = null;
        StringBuffer xmlStr = new StringBuffer(xml);

        JAXBContext context = null;
        Unmarshaller u = null;
        try {
            context = JAXBContext.newInstance(Attendance.class);
            u = context.createUnmarshaller();
            attendance = (Attendance) u.unmarshal(new StreamSource( new StringReader( xmlStr.toString() ) ));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return attendance;
    }

    public Long getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public int getWeekId() {
        return weekId;
    }

    public boolean hasPresented() {
        return presented;
    }

    public void setPresented(boolean presented) {
        this.presented = presented;
    }
}
