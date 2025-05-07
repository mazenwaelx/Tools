package com.tools.repositories;

import com.tools.entities.Post;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class PostRepository {
    @PersistenceContext(unitName = "minisocialPU")
    private EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    public void update(Post post) {
        em.merge(post);
    }

    public void delete(Post post) {
        em.remove(em.merge(post));
    }

    public List<Post> getFeedForUser(Long userId, List<Long> friendIds) {
        return em.createQuery(
                "SELECT p FROM Post p WHERE p.user.id IN :ids ORDER BY p.createdAt DESC", Post.class)
                .setParameter("ids", friendIds)
                .getResultList();
    }
}
