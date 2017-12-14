<%-- //[START imports]--%>
<%@ page import="de.ase11.attendanceTrackingSystem.model.Group" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
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

            if(current_users_group == null) {
%>
                <p>Join one of the following groups.</p>

                <form id="join-group-form" method="post">
                    <label for="group">Select Group</label>
                    <select id="group" name="joinGroupId">
                    </select>
                    <div><input id="join-group-button" type="submit" value="Join this group"/></div>
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
%>


<%@ include file="parts/footer.jsp" %>
<script src="/js/home.js"></script>
<%-- //[END all]--%>
