package com.alinahamatkhanova.ui.controllers;
import com.alinahamatkhanova.bl.services.UserService;
import com.alinahamatkhanova.infrastructure.models.Gender;
import com.alinahamatkhanova.infrastructure.models.HairColor;
import com.alinahamatkhanova.infrastructure.models.User;
import com.alinahamatkhanova.ui.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "создать пользователя")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "пользователь создан"), @ApiResponse(responseCode = "400", description = "неверные данные")})
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userService.exists(userDTO.getLogin())) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.createUser(userDTO.getLogin(), userDTO.getName(), userDTO.getAge(), userDTO.getGender(), userDTO.getHairColor());
        return ResponseEntity.status(201).body(new UserDTO(user));
    }

    @Operation(summary = "добавить друга пользователю")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "друг добавлен"), @ApiResponse(responseCode = "404", description = "пользователь не найден")})
    @PostMapping("/{login}/friends")
    public ResponseEntity<Void> addFriend(@PathVariable(name = "login") String login, @RequestBody UserDTO friendDTO) {
        User friend = new User(friendDTO.getLogin(), friendDTO.getName(), friendDTO.getAge(), friendDTO.getGender(), friendDTO.getHairColor());
        if (!userService.exists(login)) {
            return ResponseEntity.notFound().build();
        }
        userService.addFriend(login, friend);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "удалить друга пользователя")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "друг удалён"), @ApiResponse(responseCode = "404", description = "пользователь не найден")})
    @DeleteMapping("/{login}/friends/{friendLogin}")
    public ResponseEntity<Void> removeFriend(@PathVariable(name = "login") String login, @PathVariable(name = "friendLogin") String friendLogin) {
        User friend = userService.getUser(friendLogin);
        if (!userService.exists(login) || friend == null) {
            return ResponseEntity.notFound().build();
        }
        userService.removeFriend(login, friend);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "получить пользователя по логину")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "пользователь найден"), @ApiResponse(responseCode = "404", description = "пользователь не найден")})
    @GetMapping("/{login}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "login") String login) {
        User user = userService.getUser(login);
        return user != null ? ResponseEntity.ok(new UserDTO(user)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "получить всех пользователей с фильтрацией")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "пользователи найдены"), @ApiResponse(responseCode = "404", description = "пользователи не найдены")})
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsersByFilter(@RequestParam(name = "hairColor", required = false) HairColor hairColor, @RequestParam(name = "gender", required = false) Gender gender) {

        List<User> users = (hairColor != null && gender != null) ? userService.getUsersByHairColorAndGender(hairColor, gender) : userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users.stream().map(UserDTO::new).toList());
    }

    @Operation(summary = "получить список друзей пользователя по логину")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "список друзей возвращён"), @ApiResponse(responseCode = "404", description = "пользователь не найден")})
    @GetMapping("/{login}/friends")
    public ResponseEntity<List<UserDTO>> getUserFriends(@PathVariable(name = "login") String login) {
        User user = userService.getUser(login);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getFriends().stream().map(UserDTO::new).toList());
    }
}