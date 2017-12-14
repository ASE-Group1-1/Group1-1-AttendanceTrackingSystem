package de.ase11.attendanceTrackingSystem.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class AttendanceTokens {
    @Id private Long id;
    @Index private String studentId;
    private List<String> tokens = new ArrayList<String>();

    public AttendanceTokens() {};

    public static AttendanceTokens createAttendanceTokens(String studentId, int numberOfWeeks) {
        AttendanceTokens attendanceTokens = new AttendanceTokens();
        attendanceTokens.studentId = studentId;
        attendanceTokens.tokens = new ArrayList<>();

        for (int i = 0; i < numberOfWeeks; i++) {
            // TODO: Use a better Secure Random than UUID
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();
            attendanceTokens.tokens.add(token);
        }

        return attendanceTokens;
    }

    public String getAttendanceTokenByWeek(int weekNumber) {
        return this.tokens.get(weekNumber);
    }
}
