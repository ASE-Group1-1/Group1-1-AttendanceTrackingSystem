<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%-- //[START imports]--%>
<%@ page import="de.ase11.attendanceTrackingSystem.model.Group" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="parts/header.jsp" %>
<script src="/js/attendance-log-filter.js"></script>



    <h1>Attendance Log</h1>

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

<label for="limit">Limit</label>
<select id="limit">
  <option value="all">all</option>
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
  <option value="10">10</option>
  <option value="20">20</option>
  <option value="50">50</option>
  <option value="100">100</option>
</select>

<label for="group">Select Group</label>
<select id="group" name="group">
    <option value="all">all</option>
<%
    List<Group> groups = ObjectifyService.ofy()
        .load()
        .type(Group.class)
        .order("groupNumber")
        .list();

    for (Group group : groups) {
        pageContext.setAttribute("groupId", group.getId());
        pageContext.setAttribute("groupNumber", group.getGroupNumber());
%>
        <option value="${fn:escapeXml(groupId)}">${fn:escapeXml(groupNumber)}</option>
<%
    }
%>
</select>


<div id="container"></div>


<%

%>



</body>
</html>
<%-- //[END all]--%>
