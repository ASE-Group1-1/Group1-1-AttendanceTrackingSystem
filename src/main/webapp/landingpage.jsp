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

    <h1>Hello World!</h1>

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
            .type(Group.class) // We want only Greetings
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
%>
            <p>Join one of the following groups.</p>
<%
            // List all groups
            for (Group group : groups) {
                pageContext.setAttribute("group_number", group.getGroup_number());
                pageContext.setAttribute("instructor_name", group.getInstructor_name());
%>
            <p>
                <b>Group ${fn:escapeXml(group_number)}</b>
                Instructor: ${fn:escapeXml(instructor_name)}
            </p>
<%
        }
    }
}
%>


</body>
</html>
<%-- //[END all]--%>
