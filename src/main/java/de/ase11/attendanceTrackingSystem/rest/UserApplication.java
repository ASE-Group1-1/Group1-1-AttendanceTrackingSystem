package de.ase11.attendanceTrackingSystem.rest;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import de.ase11.attendanceTrackingSystem.model.Role;
import de.ase11.attendanceTrackingSystem.model.RoleType;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.routing.Router;

import java.util.List;


public class UserApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());


        Restlet join = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                String message;

                UserService userService = UserServiceFactory.getUserService();
                User user = userService.getCurrentUser();

                if(hasRole(user)) {
                    message = "This user already has a role!";
                } else {
                    String tmp = (String) request.getAttributes().get("roleType");
                    RoleType roleType = RoleType.valueOf(tmp);

                    Role role = ObjectifyService.ofy().load().type(Role.class).filter("roleType", roleType).first().now();

                    boolean result = role.join(user);

                    if (result) {
                        ObjectifyService.ofy().save().entity(role).now();

                        message = "SUCCESS";
                    } else {
                        message = "ERROR";
                    }
                }

                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        Restlet list = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                String message;

                String tmp = (String) request.getAttributes().get("roleType");
                tmp = tmp.toUpperCase();
                RoleType roleType = RoleType.valueOf(tmp);
                Role role = ObjectifyService.ofy().load().type(Role.class).filter("roleType", roleType).first().now();

                message = role.getMembers().toString();

                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        // Defines routes
        router.attach("/role/join/{roleType}", join);
        router.attach("/role/list/{roleType}", list);

        return router;
    }

    private boolean hasRole(User user) {
        List<Role> roles = ObjectifyService.ofy().load().type(Role.class).list();
        for (Role role : roles) {
            if(role.hasMember(user)) {
                return true;
            }
        }
        return false;
    }

}
