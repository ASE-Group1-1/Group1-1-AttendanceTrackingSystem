package de.ase11.attendanceTrackingSystem;

import com.googlecode.objectify.ObjectifyService;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.routing.Router;


public class AttendenceLogApplication extends Application {

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
                Attendance attendance = Attendance.createAttendanceFromXml(xml);

                ObjectifyService.ofy().save().entity(attendance).now();

                String message=attendance.getId().toString();

                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        // Defines routes
        router.attach("/test", test);
        router.attach("/list", AttendanceLogResource.class);
        router.attach("/create", createAttendance);

        return router;
    }

}
