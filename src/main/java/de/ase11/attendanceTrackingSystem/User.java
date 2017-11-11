package de.ase11.attendanceTrackingSystem;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.lang.String;

@Entity
public class User {
	@Id private Long id;
	
	private Long group_id;
	private String email;
	private String user_id;
	
	/**
	 * The @param id should be set by Objectify automatically.
	 * I'm not completely sure, yet if we should have it public or private is okay.
	 * @param email
	 * @param user_id
	 */
	public User(String email, String user_id) {
		this.email = email;
		this.user_id = user_id;
	}
	
	public void joinGroup(Long group_id) {
		// TODO Validation if this group really exists.
		// TODO Eventually validate if there is a max number of group members
		this.setGroup_id(group_id);
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the group_id
	 */
	public Long getGroup_id() {
		return group_id;
	}
	/**
	 * @param group_id the group_id to set
	 */
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
