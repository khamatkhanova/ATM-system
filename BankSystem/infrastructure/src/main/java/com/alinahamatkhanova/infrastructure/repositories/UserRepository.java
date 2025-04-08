package com.alinahamatkhanova.infrastructure.repositories;
import com.alinahamatkhanova.infrastructure.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UserRepository {

    private final EntityManagerFactory emf;

    public UserRepository() {
        this.emf = Persistence.createEntityManagerFactory("data-access-unit");
    }

    public User save(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User merged = em.merge(user);
            em.getTransaction().commit();
            return merged;
        }
        finally {
            em.close();
        }
    }

    public User findByLogin(String login) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, login);
        }
        finally {
            em.close();
        }
    }

    public void deleteByLogin(String login) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, login);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

    public boolean existsByLogin(String login) {
        return findByLogin(login) != null;
    }
}