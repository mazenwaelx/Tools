package com.tools.services;

import com.tools.entities.Post;
import com.tools.entities.User;
import com.tools.repositories.PostRepository;
import com.tools.repositories.UserRepository;
import com.tools.repositories.FriendRequestRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PostService {

    @Inject
    private PostRepository postRepo;
    @Inject
    private UserRepository userRepo;
    @Inject
    private FriendRequestRepository friendRepo;
    @Inject
    private CommentRepository commentRepo;

    public void createPost(Long userId, String content, String imageUrl) throws Exception {
        User user = userRepo.findById(userId);
        if (user == null)
            throw new Exception("User not found");
        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        post.setImageUrl(imageUrl);
        postRepo.save(post);
    }

    public List<Post> getFeed(Long userId) {
        List<Long> friendIds = friendRepo.findAcceptedUsersByUserId(userId)
                .stream().map(User::getId).collect(Collectors.toList());
        friendIds.add(userId); // include own posts
        return postRepo.getFeedForUser(userId, friendIds);
    }

    public void updatePost(Long postId, String content) throws Exception {
        Post post = postRepo.findById(postId);
        if (post == null)
            throw new Exception("Post not found");
        post.setContent(content);
        postRepo.update(post);
    }

    public void deletePost(Long postId) throws Exception {
        Post post = postRepo.findById(postId);
        if (post == null)
            throw new Exception("Post not found");
        postRepo.delete(post);
    }

    public void likePost(Long postId) throws Exception {
        Post post = postRepo.findById(postId);
        if (post == null)
            throw new Exception("Post not found");
        post.setLikes(post.getLikes() + 1);
        postRepo.update(post);
    }

    public void addComment(Long postId, Long userId, String content) throws Exception {
        Post post = postRepo.findById(postId);
        User user = userRepo.findById(userId);

        if (post == null)
            throw new Exception("Post not found");
        if (user == null)
            throw new Exception("User not found");

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);
        commentRepo.save(comment);
    }

    public List<Comment> getComments(Long postId) {
        return commentRepo.findByPostId(postId);
    }

}
