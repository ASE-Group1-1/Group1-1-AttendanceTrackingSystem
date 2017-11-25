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

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
        <script>
            var limitCount;
            var setLimit = false;
            $(document).ready(getAttendances());

            $(document).on('change','#group',function(){
                if (this.value != "all") {
                    getAttendances();
                } else {
                    getAttendances();
                }
            });

            $(document).on('change','#limit',function(){
                limitCount = parseInt(this.value);
                if (this.value != "all") {
                    setLimit = true;
                    getAttendances();
                } else {
                    setLimit = false;
                    getAttendances();
                }
            });

            function getAttendances()
            {
                $( "#container" ).empty();
                if (typeof group !== 'undefined' && group.value != "all") {
                    $.ajax({
                        type: "GET",
                        dataType: "xml",
                        url: "/rest/attendance/list/groups/" + group.value,
                        success: xmlParser
                    });
                } else {
                    $.ajax({
                        type: "GET",
                        dataType: "xml",
                        url: "/rest/attendance/list",
                        success: xmlParser
                    });
                }
            };

            function xmlParser(xml)
            {
                $(xml).find("attendance").each(function(i){
                    if(i == limitCount && setLimit == true){
                        return false;
                    }
                    $("#container").append('<div class="attendance"><p>Student: '
                        + $(this).find("studentId").text()
                        + '<br> Group: '
                        + $(this).find("groupId").text()
                        + '<br> Week: '
                        + $(this).find("weekId").text()
                        + '<br> Presented: '
                        + $(this).find("presented").text()
                        + '</p></div>');
                    $(".attendance").fadeIn(50);
                });
            };

        </script>
</head>

<body>

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
