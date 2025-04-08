package com.alinahamatkhanova.infrastructure.models;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "hair_color", nullable = false)
    private HairColor hairColor;

    @ManyToMany
    @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_login"), inverseJoinColumns = @JoinColumn(name = "friend_login"))
    private Set<User> friends = new HashSet<>();

    public User() {}

    public User(String login, String name, int age, Gender gender, HairColor hairColor) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
        this.friends = new HashSet<>();
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
    }
}