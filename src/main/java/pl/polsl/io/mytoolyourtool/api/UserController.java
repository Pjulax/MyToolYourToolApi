package pl.polsl.io.mytoolyourtool.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.*;
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
    public void createUser(@RequestBody SignUpDTO signUpDTO){
        userService.createUser(signUpDTO);
    }

    @GetMapping("/me")
    public UserDetailsDTO getUserDetails(){
        return userService.getUserDetails();
    }

    @DeleteMapping("/delete-me")
    public void deleteMyAccount() {
        userService.deleteUserAccount();
    }

        @GetMapping(path = "/my-history")
        public List<HistoryDTO>getHistory(){return userService.getHistory();}
}
