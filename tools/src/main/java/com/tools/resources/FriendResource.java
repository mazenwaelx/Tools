package com.tools.resources;

import com.tools.dtos.FriendRequestDTO;
import com.tools.entities.FriendRequest;
import com.tools.entities.User;
import com.tools.services.FriendService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/friends")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FriendResource {

    @Inject
    private FriendService friendService;

    @POST
    @Path("/send")
    public Response sendRequest(FriendRequestDTO requestDTO) {
        try {
            FriendRequest friendRequest = friendService.sendFriendRequest(requestDTO.getSenderId(), requestDTO.getReceiverId());
            Long requestId = friendRequest.getId();
            return Response.ok("{\"message\":\"Friend request sent successfully.\", \"requestId\":" + requestId + "}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }




    @PUT
    @Path("/accept/{requestId}")
    public Response acceptRequest(@PathParam("requestId") Long requestId) {
        try {
            friendService.acceptFriendRequest(requestId);
            return Response.ok("{\"message\":\"Friend request accepted.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @PUT
    @Path("/reject/{requestId}")
    public Response rejectRequest(@PathParam("requestId") Long requestId) {
        try {
            friendService.rejectFriendRequest(requestId);
            return Response.ok("{\"message\":\"Friend request rejected.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @GET
    @Path("/{userId}/received")
    public Response viewReceivedRequests(@PathParam("userId") Long userId) {
        try {
            List<FriendRequest> requests = friendService.getReceivedFriendRequests(userId);
            return Response.ok(requests).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @GET
    @Path("/{userId}/sent")
    public Response viewSentRequests(@PathParam("userId") Long userId) {
        try {
            List<FriendRequest> requests = friendService.getSentFriendRequests(userId);
            return Response.ok(requests).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @GET
    @Path("/{userId}/all")
    public Response viewAllFriends(@PathParam("userId") Long userId) {
        try {
            List<User> friends = friendService.getAllFriends(userId);
            return Response.ok(friends).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }
}
