package com.alinahamatkhanova.infrastructure.repositories;
import com.alinahamatkhanova.infrastructure.models.User;
import com.alinahamatkhanova.infrastructure.models.Gender;
import com.alinahamatkhanova.infrastructure.models.HairColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);
    boolean existsByLogin(String login);
    List<User> findByHairColorAndGender(HairColor hairColor, Gender gender);
}