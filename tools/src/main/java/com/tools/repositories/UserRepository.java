package com.tools.repositories;

import com.tools.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserRepository {

    @PersistenceContext(unitName = "minisocialPU")
    private EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findByEmail(String email) {
        List<User> users = em.createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email)
                .getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public void update(User user) {
        em.merge(user);
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
