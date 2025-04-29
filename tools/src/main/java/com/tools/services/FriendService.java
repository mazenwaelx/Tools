package com.tools.services;

import com.tools.entities.FriendRequest;
import com.tools.entities.User;
import com.tools.repositories.FriendRequestRepository;
import com.tools.repositories.UserRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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
        FriendRequest request = new FriendRequest();
        request.setSender(userRepository.findById(senderId));
        request.setReceiver(userRepository.findById(receiverId));
        request.setStatus("pending");
        return friendRequestRepository.save(request);
    }


    public List<FriendRequest> getSentFriendRequests(Long userId) {
        return friendRequestRepository.findBySenderId(userId);
    }

    public List<FriendRequest> getReceivedFriendRequests(Long userId) {
        return friendRequestRepository.findByReceiverId(userId);
    }

    public void acceptFriendRequest(Long requestId) throws Exception {
        FriendRequest request = friendRequestRepository.findById(requestId);
        if (request == null) {
            throw new Exception("Request not found.");
        }
        request.setStatus("ACCEPTED");
        friendRequestRepository.update(request);
    }

    public void rejectFriendRequest(Long requestId) throws Exception {
        FriendRequest request = friendRequestRepository.findById(requestId);
        if (request == null) {
            throw new Exception("Request not found.");
        }
        request.setStatus("REJECTED");
        friendRequestRepository.update(request);
    }

    public List<User> getFriendsList(Long userId) {
        List<FriendRequest> accepted = friendRequestRepository.findAcceptedByUserId(userId);
        return accepted.stream()
                       .map(req -> req.getSender().getId().equals(userId) ? req.getReceiver() : req.getSender())
                       .collect(Collectors.toList());
    }
    public List<User> getAllFriends(Long userId) {
        List<FriendRequest> accepted = friendRequestRepository.findAcceptedByUserId(userId);
        return accepted.stream()
                       .map(req -> req.getSender().getId().equals(userId) ? req.getReceiver() : req.getSender())
                       .collect(Collectors.toList());
    }

}
