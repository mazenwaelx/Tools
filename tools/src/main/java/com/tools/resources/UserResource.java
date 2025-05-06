package com.tools.resources;

import com.tools.dtos.UserDTO;
import com.tools.entities.User;
import com.tools.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/register")
    public Response register(UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return Response.ok("{\"message\":\"User registered successfully.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(UserDTO userDTO) {
        try {
            User user = userService.loginUser(userDTO.getEmail(), userDTO.getPassword());
            return Response.ok("{\"message\":\"Login successful.\",\"token\":\"fake-jwt-token\",\"userId\":" + user.getId() + "}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @PUT
    @Path("/{userId}/update")
    public Response update(@PathParam("userId") Long userId, UserDTO userDTO) {
        try {
            userService.updateProfile(userId, userDTO);
            return Response.ok("{\"message\":\"Profile updated successfully.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @GET
    @Path("/id/{email}")
    public Response getUserIdByEmail(@PathParam("email") String email) {
        try {
            User user = userService.getUserByEmail(email);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\":\"User not found.\"}")
                               .build();
            }
            return Response.ok("{\"userId\":" + user.getId() + "}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @GET
    public Response getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return Response.ok(users).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }
}
