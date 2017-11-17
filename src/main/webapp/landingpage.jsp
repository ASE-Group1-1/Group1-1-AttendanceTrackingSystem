<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%-- //[START imports]--%>
<%@ page import="de.ase11.attendanceTrackingSystem.Group" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>

    <h1>Welcome to the Attendance Tracking System!</h1>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);
%>

    <p>
        Hello, ${fn:escapeXml(user.nickname)}! (You can
        <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)
    </p>
<%
    } else {
%>
    <p>
        Hello!
        <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
        to join a group.
    </p>
<%
    }
%>

<%
    if (user != null) {
        List<Group> groups = ObjectifyService.ofy()
            .load()
            .type(Group.class)
            .order("group_number")
            .list();

        if (groups.isEmpty()) {
%>
        <p>There are no groups, yet.</p>
        <form action="/create-groups" method="post">
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
<%
                // List all groups
                for (Group group : groups) {
                    pageContext.setAttribute("group_id", group.getId());
                    pageContext.setAttribute("group_number", group.getGroup_number());
                    pageContext.setAttribute("instructor_name", group.getInstructor_name());
%>
                <p>
                    <b>Group ${fn:escapeXml(group_number)}</b>
                    Instructor: ${fn:escapeXml(instructor_name)} Group-Id: ${fn:escapeXml(group_id)}
                    <form action="/join-group" method="post">
                        <div><input type="submit" value="Join this group"/></div>
                        <input type="hidden" name="joinGroupId" value="${fn:escapeXml(group_id)}"/>
                    </form>
                </p>
<%
                }
            } else {
                pageContext.setAttribute("group_number", current_users_group.getGroup_number());
                pageContext.setAttribute("instructor_name", current_users_group.getInstructor_name());
%>
                <p>You are member in group ${fn:escapeXml(group_number)}</p>
                <p>Instructor: ${fn:escapeXml(instructor_name)}</p>
<%
            }
        }
    }
%>


</body>
</html>
<%-- //[END all]--%>
