package de.ase11.attendanceTrackingSystem;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import com.google.appengine.api.users.User;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Group {
	@Id private Long id;

	@Index private int groupNumber;
	private String meetingLocation;
	private String meetingTime;
	private String instructorName;
    private List<User> members = new ArrayList<User>();

	public Group() {
	}
	
	/**
	 * The @param id should be set by Objectify automatically.
	 * I'm not completely sure, yet if we should have it public or private is okay.
	 * @param groupNumber
	 * @param meetingLocation
	 * @param meetingTime
	 * @param instructorName
	 */
	public Group(int groupNumber, String meetingLocation, String meetingTime, String instructorName) {
		this.groupNumber = groupNumber;
		this.meetingLocation = meetingLocation;
		this.meetingTime = meetingTime;
		this.instructorName = instructorName;
	}
	
	public boolean joinGroup (User user) {
	    boolean valid_join = true;
	    valid_join = !(this.hasMember(user));

	    if (valid_join) {
	        this.members.add(user);
	        return true;
        } else {
	        return false;
        }
    }

    public boolean hasMember (User user) {
        if (this.members.contains(user)) return true;
        else return false;
    }
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the groupNumber
	 */
	public int getGroupNumber() {
		return groupNumber;
	}
	/**
	 * @param groupNumber the groupNumber to set
	 */
	private void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}
	/**
	 * @return the meetingLocation
	 */
	public String getMeetingLocation() {
		return meetingLocation;
	}
	/**
	 * @param meetingLocation the meetingLocation to set
	 */
	private void setMeetingLocation(String meetingLocation) {
		this.meetingLocation = meetingLocation;
	}
	/**
	 * @return the meetingTime
	 */
	public String getMeetingTime() {
		return meetingTime;
	}
	/**
	 * @param meetingTime the meetingTime to set
	 */
	private void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}
	/**
	 * @return the instructorName
	 */
	public String getInstructorName() {
		return instructorName;
	}
	/**
	 * @param instructorName the instructorName to set
	 */
	private void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}
    /**
     * @return the members
     */
    public List<User> getMembers() { return members; }
    /**
     * @param members the members to set
     */
    private void setMembers(List<User> members) { this.members = members; }
}
