package pl.polsl.io.mytoolyourtool.utils;

import org.springframework.stereotype.Component;
import pl.polsl.io.mytoolyourtool.api.dto.SignUpDTO;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;
import pl.polsl.io.mytoolyourtool.utils.exception.ObjectExistsException;

@Component
public class DataLoader {

    private final UserService userService;

    public DataLoader(UserService userService) {
        this.userService = userService;
        LoadUsers();
    }

    private void LoadUsers() {
        try {
            userService.createUser(new SignUpDTO("deleted@user.com", "password", "Deleted", "User"));
        }
        catch ( ObjectExistsException | IllegalArgumentException ex ) {
            return;
        }
    }
}