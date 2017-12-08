package de.ase11.attendanceTrackingSystem.rest;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import de.ase11.attendanceTrackingSystem.GroupList;
import de.ase11.attendanceTrackingSystem.model.Group;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Form;
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

        Restlet createInitialSet = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                String message;
                GroupList groupList = GroupList.createGroupList();

                if(groupList.isEmpty()) {
                    Group group1 = new Group(1, "Room 1", "Monday 9:00am", "Max");
                    Group group2 = new Group(2, "Room 2", "Tuesday 9:00am", "Ana");
                    Group group3 = new Group(3, "Room 3", "Wednesday 9:00am", "Max");
                    Group group4 = new Group(4, "Room 4", "Thursday 9:00am", "Ana");
                    Group group5 = new Group(5, "Room 5", "Friday 9:00am", "Max");

                    ObjectifyService.ofy().save().entity(group1).now();
                    ObjectifyService.ofy().save().entity(group2).now();
                    ObjectifyService.ofy().save().entity(group3).now();
                    ObjectifyService.ofy().save().entity(group4).now();
                    ObjectifyService.ofy().save().entity(group5).now();

                    message = "SUCCESS";
                } else {
                    message = "Initial set of groups already existed before!";
                }
                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        Restlet join = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                String message;

                UserService userService = UserServiceFactory.getUserService();
                User user = userService.getCurrentUser();  // Find out who the user is.

                if(user != null) {
                    Form form = new Form(request.getEntity());
                    String joinGroupId = form.getValues("joinGroupId");
                    Long groupId = Long.valueOf(joinGroupId);
                    Group group = ObjectifyService.ofy().load().type(Group.class).id(groupId).now();

                    boolean join = group.joinGroup(user);

                    if(join) {
                        ObjectifyService.ofy().save().entity(group).now();
                        message="SUCCESS";
                    } else {
                        message="ERROR";
                    }
                } else {
                    message = ("errorCode=1");
                }

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
        router.attach("/create/initial-set", createInitialSet);
        router.attach("/join", join);
        router.attach("/list", list);

        return router;
    }

}
