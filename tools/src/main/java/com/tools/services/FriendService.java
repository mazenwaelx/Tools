package com.tools.services;

import com.tools.entities.FriendRequest;
import com.tools.entities.User;
import com.tools.repositories.FriendRequestRepository;
import com.tools.repositories.UserRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class FriendService {

    @Inject
    private FriendRequestRepository friendRequestRepository;

    @Inject
    private UserRepository userRepository;

    public FriendRequest sendFriendRequest(Long senderId, Long receiverId) throws Exception {
        if (senderId.equals(receiverId)) {
            throw new Exception("Cannot send friend request to yourself.");
        }

        if (friendRequestRepository.findByUsers(senderId, receiverId) != null) {
            throw new Exception("Friend request already exists.");
        }

        User sender = userRepository.findById(senderId);
        User receiver = userRepository.findById(receiverId);

        if (sender == null || receiver == null) {
            throw new Exception("Sender or receiver not found.");
        }

        FriendRequest request = new FriendRequest(sender, receiver);
        request.setStatus("PENDING");
        return friendRequestRepository.save(request);
    }

    public void acceptFriendRequest(Long requestId) throws Exception {
        FriendRequest request = friendRequestRepository.findById(requestId);
        if (request == null || !"PENDING".equals(request.getStatus())) {
            throw new Exception("Invalid friend request.");
        }
        request.setStatus("ACCEPTED");
        friendRequestRepository.update(request);
    }

    public void rejectFriendRequest(Long requestId) throws Exception {
        FriendRequest request = friendRequestRepository.findById(requestId);
        if (request == null || !"PENDING".equals(request.getStatus())) {
            throw new Exception("Invalid friend request.");
        }
        request.setStatus("REJECTED");
        friendRequestRepository.update(request);
    }

    public List<FriendRequest> getReceivedFriendRequests(Long userId) {
        return friendRequestRepository.findByReceiverId(userId);
    }

    public List<FriendRequest> getSentFriendRequests(Long userId) {
        return friendRequestRepository.findBySenderId(userId);
    }

    public List<User> getAllFriends(Long userId) {
        return friendRequestRepository.findAcceptedUsersByUserId(userId);
    }
}
