/**
 * Copyright 2014-2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//[START all]
package de.ase11.attendanceTrackingSystem;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Form Handling Servlet
 * This servlet will take http requests and process them.
 */
public class GroupCreationServlet extends HttpServlet {

    // Process the http POST of the form
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();  // Find out who the user is.

        Group group1 = new Group(1,"Room 1", "Monday 9:00am", "Max");
        Group group2 = new Group(2,"Room 2", "Tuesday 9:00am", "Ana");
        Group group3 = new Group(3,"Room 3", "Wednesday 9:00am", "Max");
        Group group4 = new Group(4,"Room 4", "Thursday 9:00am", "Ana");
        Group group5 = new Group(5,"Room 5", "Friday 9:00am", "Max");

        ObjectifyService.ofy().save().entity(group1).now();
        ObjectifyService.ofy().save().entity(group2).now();
        ObjectifyService.ofy().save().entity(group3).now();
        ObjectifyService.ofy().save().entity(group4).now();
        ObjectifyService.ofy().save().entity(group5).now();

        resp.sendRedirect("/landingpage.jsp");
    }
}
//[END all]
