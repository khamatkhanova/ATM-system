package com.alinahamatkhanova.ui.dto;
import com.alinahamatkhanova.infrastructure.models.Gender;
import com.alinahamatkhanova.infrastructure.models.HairColor;
import com.alinahamatkhanova.infrastructure.models.User;

public class UserDTO {
    private String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;

    public UserDTO(){}
    public UserDTO(User user) {
        this.login = user.getLogin();
        this.name = user.getName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.hairColor = user.getHairColor();
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
}