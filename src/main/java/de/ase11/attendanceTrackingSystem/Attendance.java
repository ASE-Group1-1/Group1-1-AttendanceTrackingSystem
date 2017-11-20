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
    @XmlElement(name = "student_id")
    //TODO: Change student_id to string
    private Long student_id;
    @XmlElement(name = "group_id")
    private Long group_id;
    @XmlElement(name = "week_id")
    private int week_id;
    @XmlElement(name = "presented")
    private boolean presented;

    public Attendance() {};

    public Attendance(Long student_id, Long group_id, int week_id) {
        this.student_id = student_id;
        this.group_id = group_id;
        this.week_id = week_id;
    }

    public Attendance(Long student_id, Long group_id, int week_id, boolean presented) {
        this.student_id = student_id;
        this.group_id = group_id;
        this.week_id = week_id;
        this.presented = presented;
    }


    public Long getId() {
        return id;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public int getWeek_id() {
        return week_id;
    }

    public boolean hasPresented() {
        return presented;
    }

    public void setPresented(boolean presented) {
        this.presented = presented;
    }
}
