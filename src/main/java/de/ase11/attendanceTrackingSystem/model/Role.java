package de.ase11.attendanceTrackingSystem.model;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    private Long id;
    @Index
    private RoleType roleType;
    private List<User> members = new ArrayList<>();

    public Role() {
    }

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public boolean join(User user) {
        if (this.hasMember(user)){
            return false;
        } else {
            return this.members.add(user);
        }
    }

    public boolean hasMember(User user) {
        return this.members.contains(user);
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public List<User> getMembers() {
        return members;
    }
}
