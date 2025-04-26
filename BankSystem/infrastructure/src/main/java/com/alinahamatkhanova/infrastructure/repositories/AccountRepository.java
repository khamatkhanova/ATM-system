package com.alinahamatkhanova.infrastructure.repositories;
import com.alinahamatkhanova.infrastructure.models.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountRepository {

    private final EntityManagerFactory emf;

    public AccountRepository() {
        this.emf = Persistence.createEntityManagerFactory("data-access-unit");
    }

    public Account save(Account account) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Account merged = em.merge(account);
            em.getTransaction().commit();
            return merged;
        }
        finally {
            em.close();
        }
    }

    public Account findById(String accountId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Account.class, accountId);
        }
        finally {
            em.close();
        }
    }

    public void deleteById(String accountId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Account account = em.find(Account.class, accountId);
            if (account != null) {
                em.remove(account);
            }
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

    public boolean existsById(String accountId) {
        return findById(accountId) != null;
    }

    public Map<String, Account> findByUserLogin(String userLogin) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Account> result = em.createQuery("SELECT a FROM Account a WHERE a.userLogin = :userLogin", Account.class).setParameter("userLogin", userLogin).getResultList();
            return result.stream().collect(Collectors.toMap(Account::getId, a -> a));
        }
        finally {
            em.close();
        }
    }
}