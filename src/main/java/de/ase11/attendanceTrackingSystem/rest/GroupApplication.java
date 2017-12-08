package de.ase11.attendanceTrackingSystem.rest;

import de.ase11.attendanceTrackingSystem.GroupList;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.routing.Router;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class GroupApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());


        Restlet create = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                String message = "This request needs to be implemented";

                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        Restlet list = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                String message;

                try {
                    GroupList groupList = GroupList.createGroupList();
                    message = groupList.groupListToXml();
                } catch(JAXBException | IOException e) {
                    message = "ERROR";
                }

                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        // Defines routes
        router.attach("/create", create);
        router.attach("/list", list);

        return router;
    }

}
