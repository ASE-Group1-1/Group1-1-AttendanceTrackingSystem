<%-- //[START imports]--%>
<%@ page import="de.ase11.attendanceTrackingSystem.model.Group" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="parts/header.jsp" %>




    <h1>Attendance Log</h1>


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


<%@ include file="parts/footer.jsp" %>
<script src="/js/attendance-log-filter.js"></script>
<%-- //[END all]--%>