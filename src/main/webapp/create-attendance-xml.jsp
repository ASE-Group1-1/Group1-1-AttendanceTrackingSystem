<%@ include file="parts/header.jsp" %>

        <h1>Create an Attendance</h1>

        <form action="/rest/attendance/create" method="post">
            <div class="form-group">
                <label for="attendance">Enter the xml that you want to send</label>
                <textarea rows="8" class="form-control" name="attendance" id="attendance">
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xs:attendance xmlns:xs="http://www.w3.org/2001/XMLSchema">
<studentId>test@example.com</studentId>
<groupId>4785074604081152</groupId>
<weekId>0</weekId>
<presented>true</presented>
</xs:attendance>
                </textarea>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>

<%@ include file="parts/footer.jsp" %>