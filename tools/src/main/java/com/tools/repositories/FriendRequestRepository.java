package com.tools.repositories;

import com.tools.entities.FriendRequest;
import com.tools.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class FriendRequestRepository {

    @PersistenceContext(unitName = "minisocialPU")
    private EntityManager em;

    
    public FriendRequest save(FriendRequest request) {
        em.persist(request);
        return request;
    }

    public FriendRequest findById(Long id) {
        return em.find(FriendRequest.class, id);
    }

    public void update(FriendRequest request) {
        em.merge(request);
    }

    public FriendRequest findByUsers(Long senderId, Long receiverId) {
        List<FriendRequest> requests = em.createQuery(
                "SELECT r FROM FriendRequest r WHERE r.sender.id = :senderId AND r.receiver.id = :receiverId",
                FriendRequest.class)
            .setParameter("senderId", senderId)
            .setParameter("receiverId", receiverId)
            .getResultList();
        return requests.isEmpty() ? null : requests.get(0);
    }

    public List<FriendRequest> findByReceiverId(Long userId) {
        return em.createQuery(
                "SELECT r FROM FriendRequest r WHERE r.receiver.id = :userId AND r.status = 'PENDING'",
                FriendRequest.class)
            .setParameter("userId", userId)
            .getResultList();
    }

    public List<FriendRequest> findBySenderId(Long userId) {
        return em.createQuery(
                "SELECT r FROM FriendRequest r WHERE r.sender.id = :userId",
                FriendRequest.class)
            .setParameter("userId", userId)
            .getResultList();
    }

    public List<User> findAcceptedUsersByUserId(Long userId) {
        return em.createQuery(
                "SELECT CASE WHEN r.sender.id = :userId THEN r.receiver ELSE r.sender END " +
                "FROM FriendRequest r WHERE (r.sender.id = :userId OR r.receiver.id = :userId) AND r.status = 'ACCEPTED'",
                User.class)
            .setParameter("userId", userId)
            .getResultList();
    }
}
