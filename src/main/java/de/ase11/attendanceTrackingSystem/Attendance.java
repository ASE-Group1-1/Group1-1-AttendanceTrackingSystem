package de.ase11.attendanceTrackingSystem;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
