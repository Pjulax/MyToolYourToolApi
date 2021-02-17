package pl.polsl.io.mytoolyourtool.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.LoginDTO;
import pl.polsl.io.mytoolyourtool.api.dto.SignUpDTO;
import pl.polsl.io.mytoolyourtool.api.dto.UserDetailsDTO;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;

import java.util.List;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = "application/json")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping(path = "/login")
    public String login(@RequestBody LoginDTO credentials) {
        return userService.login(credentials);
    }

    @PostMapping("/signup")
    public User createUser(@RequestBody SignUpDTO signUpDTO){
        return userService.createUser(signUpDTO);
    }

    @GetMapping("/me")
    public UserDetailsDTO getUserDetails(){
        return userService.getUserDetails();
    }
}
