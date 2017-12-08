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
</select>

<div id="attendance-container"></div>

<%@ include file="parts/footer.jsp" %>
<script src="/js/attendance-log-filter.js"></script>
<%-- //[END all]--%>