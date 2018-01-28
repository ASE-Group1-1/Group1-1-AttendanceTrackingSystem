<%-- //[START imports]--%>
<%@ page import="de.ase11.attendanceTrackingSystem.model.Group" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="java.util.List" %>
<%@ page import="de.ase11.attendanceTrackingSystem.model.Role" %>
<%-- //[END imports]--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<%@ include file="parts/header.jsp" %>

    <h1>Welcome to the Attendance Tracking System!</h1>

<%
    if (user != null) {
        pageContext.setAttribute("user", user);
%>

    <p>
        Hello, ${fn:escapeXml(user.nickname)}!
    </p>
<%
    } else {
%>
    <p>
        Hello! Sign in to join a group.
    </p>
<%
    }
%>

<%
    if (user != null) {
        List<Group> groups = ObjectifyService.ofy()
            .load()
            .type(Group.class)
            .order("groupNumber")
            .list();

        if (groups.isEmpty()) {
%>
        <p>There are no groups, yet.</p>
        <form action="/rest/group/create/initial-set" method="post">
            <div><input type="submit" value="Create Initial Set of Groups"/></div>
            <input type="hidden" name="createGroups" value="true"/>
        </form>
<%
        } else {
            // Check if the current user is already member in a group
            Group current_users_group = null;
            for (Group group : groups) {
                if(group.hasMember(user)) {
                    current_users_group = group;
                }
            }

            List<Role> roles = ObjectifyService.ofy().load().type(Role.class).list();
            boolean userHasRole = false;
            for (Role role : roles) {
                if(role.hasMember(user)) {
                    userHasRole = true;
                }
            }
            if (!userHasRole) {
%>

            Please choose your role:
            <br><br>
            <form action="/rest/user/role/join/STUDENT" method="post">
                <div><input id="join-group-button" class="btn btn-primary" type="submit" value="Student"/></div>
            </form>

            <form action="/rest/user/role/join/TUTOR" method="post">
                <div style="margin-top:20px"><input id="join-group-button" class="btn btn-primary" type="submit" value="Tutor"/></div>
            </form>



<%
            } else {
                if(current_users_group == null) {
%>
                    <p>Join one of the following groups.</p>

                    <form id="join-group-form" method="post">
                        <label for="select-group">Please select:</label>
                        <div id="select-group">
                        </div>
                        <div><input id="join-group-button" class="btn btn-primary" type="submit" value="Join this group"/></div>
                    </form>
<%
                } else {
                    pageContext.setAttribute("groupId", current_users_group.getId());
                    pageContext.setAttribute("groupNumber", current_users_group.getGroupNumber());
                    pageContext.setAttribute("instructorName", current_users_group.getInstructorName());
                    pageContext.setAttribute("meetingTime", current_users_group.getMeetingTime());
                    pageContext.setAttribute("meetingLocation", current_users_group.getMeetingLocation());
%>
                    <p>ID: ${fn:escapeXml(groupId)}</p>
                    <p>You are member in group ${fn:escapeXml(groupNumber)}</p>
                    <p>Instructor: ${fn:escapeXml(instructorName)}</p>
                    <p>Time: ${fn:escapeXml(meetingTime)}</p>
                    <p>Location: ${fn:escapeXml(meetingLocation)}</p>
<%
                }
            }
        }
    }
%>


<%@ include file="parts/footer.jsp" %>
<script src="/js/home.js"></script>
<%-- //[END all]--%>
