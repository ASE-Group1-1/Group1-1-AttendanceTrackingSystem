package de.ase11.attendanceTrackingSystem.model;

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
    @XmlElement(name = "attendanceToken")
    private String attendanceToken;

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

    public static Attendance createAttendanceFromXml(String xml) {
        Attendance attendance = Attendance.xmlToAttendance(xml);

        String studentId = attendance.getStudentId();
        User user = new User(studentId,"gmail.com");

        List<Group> groups = ObjectifyService.ofy().load().type(Group.class).list();
        Group current_users_group = null;
        for (Group group : groups) {
            if(group.hasMember(user)) {
                current_users_group = group;
            }
        }


        AttendanceTokens attendanceTokens = ObjectifyService.ofy().load().type(AttendanceTokens.class).filter("studentEmail", user.getEmail()).first().now();
        String token1 = attendance.getAttendanceToken();
        String token2 = attendanceTokens.getAttendanceTokenByWeek(attendance.getWeekId());
        boolean validToken = token1.equals(token2);

        if(current_users_group.getId().equals(attendance.getGroupId()) && validToken) {
            return attendance;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static Attendance xmlToAttendance(String xml) {

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

    public String getAttendanceToken() {
        return attendanceToken;
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
