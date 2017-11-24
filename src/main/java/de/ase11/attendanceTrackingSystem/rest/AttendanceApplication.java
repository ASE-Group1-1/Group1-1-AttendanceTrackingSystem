package de.ase11.attendanceTrackingSystem.rest;

import com.googlecode.objectify.ObjectifyService;
import de.ase11.attendanceTrackingSystem.AttendanceLog;
import de.ase11.attendanceTrackingSystem.model.Attendance;
import de.ase11.attendanceTrackingSystem.rest.AttendanceLogResource;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.routing.Router;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public class AttendanceApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        Restlet test = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                String message = "Hello World!";
                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        Restlet createAttendance = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                Form form = new Form(request.getEntity());
                String xml = form.getValues("attendance");
                String message;

                try {
                    Attendance attendance = Attendance.createAttendanceFromXml(xml);
                    ObjectifyService.ofy().save().entity(attendance).now();

                    message = attendance.getId().toString();
                } catch (IllegalArgumentException e) {
                    message = "The specified user is not a member of this group!";
                }

                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        Restlet groups = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                String message;

                String tmp = (String) request.getAttributes().get("groupId");
                Long groupId = new Long(tmp);

                try {
                    AttendanceLog attendanceLog = AttendanceLog.createGroupAttendanceLog(groupId);

                    message = attendanceLog.attendanceLogToXml();
                } catch(JAXBException | IOException e) {
                    message = "ERROR";
                }

                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        Restlet users = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                String message;

                String userId = (String) request.getAttributes().get("userId");

                try {
                    AttendanceLog attendanceLog = AttendanceLog.createUserAttendanceLog(userId);

                    message = attendanceLog.attendanceLogToXml();
                } catch(JAXBException | IOException e) {
                    message = "ERROR";
                }

                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        // Defines routes
        router.attach("/test", test);
        router.attach("/create", createAttendance);
        // TODO: Choose one way of handling requests either using the Resource or doing it without.
        // Either all of them should be moved to the AttendanceLogResource class or
        // the class should be completely removed and everything should be done in here.
        router.attach("/list", AttendanceLogResource.class);
        router.attach("/list/groups/{groupId}", groups);
        router.attach("/list/users/{userId}", users);

        return router;
    }

}
