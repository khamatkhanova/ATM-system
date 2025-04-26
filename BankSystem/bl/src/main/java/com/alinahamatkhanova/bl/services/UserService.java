package com.alinahamatkhanova.bl.services;
import com.alinahamatkhanova.infrastructure.models.Gender;
import com.alinahamatkhanova.infrastructure.models.HairColor;
import com.alinahamatkhanova.infrastructure.models.User;
import com.alinahamatkhanova.infrastructure.repositories.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String login, String name, int age, Gender gender, HairColor hairColor) {
        User user = new User(login, name, age, gender, hairColor);
        userRepository.save(user);
        return user;
    }

    public User getUser(String login) {
        return userRepository.findByLogin(login);
    }

    public void addFriend(String login, User friend) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            user.addFriend(friend);
        }
    }

    public void removeFriend(String login, User friend) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            user.removeFriend(friend);
        }
    }

    public boolean exists(String login) {
        return userRepository.existsByLogin(login);
    }
}