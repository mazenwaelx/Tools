package com.tools.repositories;

import com.tools.entities.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CommentRepository {

    @PersistenceContext(unitName = "minisocialPU")
    private EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public List<Comment> findByPostId(Long postId) {
        return em.createQuery("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.createdAt", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }
}
