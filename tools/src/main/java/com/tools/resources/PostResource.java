package com.tools.resources;

import com.tools.dtos.PostDTO;
import com.tools.entities.Post;
import com.tools.services.PostService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    @Inject
    private PostService postService;

    @POST
    public Response create(PostDTO dto) {
        try {
            postService.createPost(dto.getUserId(), dto.getContent(), dto.getImageUrl());
            return Response.ok("{\"message\":\"Post created.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/feed/{userId}")
    public Response getFeed(@PathParam("userId") Long userId) {
        List<Post> feed = postService.getFeed(userId);
        return Response.ok(feed).build();
    }

    @PUT
    @Path("/{postId}")
    public Response update(@PathParam("postId") Long id, String content) {
        try {
            postService.updatePost(id, content);
            return Response.ok("{\"message\":\"Post updated.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @DELETE
    @Path("/{postId}")
    public Response delete(@PathParam("postId") Long id) {
        try {
            postService.deletePost(id);
            return Response.ok("{\"message\":\"Post deleted.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("/like/{postId}")
    public Response like(@PathParam("postId") Long id) {
        try {
            postService.likePost(id);
            return Response.ok("{\"message\":\"Post liked.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
}
