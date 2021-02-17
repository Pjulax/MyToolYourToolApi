package pl.polsl.io.mytoolyourtool.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.LoginDTO;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;

import java.util.List;


@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService=userService;
    }


    @GetMapping(produces = "application/json")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
    userService.addNewUser(user);
    }

    @PostMapping(path = "/login")
    public String login(@RequestBody LoginDTO credentials) {
        return userService.login(credentials);
    }
}
