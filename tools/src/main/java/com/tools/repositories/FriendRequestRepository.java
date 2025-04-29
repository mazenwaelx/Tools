package com.tools.repositories;

import com.tools.entities.FriendRequest;
import com.tools.entities.User;

import javax.ejb.Stateless;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class FriendRequestRepository {

    @PersistenceContext(unitName = "toolsPU")
    private EntityManager em;

    public FriendRequest save(FriendRequest request) {
        em.persist(request);
        em.flush();
        return request;
    }


    public void update(FriendRequest request) {
        em.merge(request);
    }

    public FriendRequest findById(Long id) {
        return em.find(FriendRequest.class, id);
    }

    public List<FriendRequest> findBySenderId(Long senderId) {
        return em.createQuery("SELECT r FROM FriendRequest r WHERE r.sender.id = :senderId", FriendRequest.class)
                 .setParameter("senderId", senderId)
                 .getResultList();
    }

    public List<FriendRequest> findByReceiverId(Long receiverId) {
        return em.createQuery("SELECT r FROM FriendRequest r WHERE r.receiver.id = :receiverId", FriendRequest.class)
                 .setParameter("receiverId", receiverId)
                 .getResultList();
    }

    public FriendRequest findByUsers(Long senderId, Long receiverId) {
        List<FriendRequest> list = em.createQuery(
                "SELECT r FROM FriendRequest r WHERE r.sender.id = :senderId AND r.receiver.id = :receiverId", FriendRequest.class)
                .setParameter("senderId", senderId)
                .setParameter("receiverId", receiverId)
                .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    public List<FriendRequest> findAcceptedByUserId(Long userId) {
        return em.createQuery(
            "SELECT fr FROM FriendRequest fr " +
            "WHERE (fr.sender.id = :userId OR fr.receiver.id = :userId) AND fr.status = 'ACCEPTED'",
            FriendRequest.class
        )
        .setParameter("userId", userId)
        .getResultList();
    }



}
