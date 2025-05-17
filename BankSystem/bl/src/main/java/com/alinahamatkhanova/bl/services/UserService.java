package com.alinahamatkhanova.bl.services;
import com.alinahamatkhanova.infrastructure.models.Gender;
import com.alinahamatkhanova.infrastructure.models.HairColor;
import com.alinahamatkhanova.infrastructure.models.User;
import com.alinahamatkhanova.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String login, String name, int age, Gender gender, HairColor hairColor) {
        User user = new User(login, name, age, gender, hairColor);
        return userRepository.save(user);
    }

    public User getUser(String login) {
        return userRepository.findByLogin(login);
    }

    public void addFriend(String login, User friend) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            user.addFriend(friend);
            userRepository.save(user);
        }
    }

    public void removeFriend(String login, User friend) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            user.removeFriend(friend);
            userRepository.save(user);
        }
    }

    public boolean exists(String login) {
        return userRepository.existsByLogin(login);
    }

    public List<User> getUsersByHairColorAndGender(HairColor hairColor, Gender gender) {
        return userRepository.findByHairColorAndGender(hairColor, gender);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}