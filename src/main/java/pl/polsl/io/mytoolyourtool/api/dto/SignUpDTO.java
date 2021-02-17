package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpDTO {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
}
