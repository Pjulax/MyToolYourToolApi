package pl.polsl.io.mytoolyourtool.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpDTO {
    @ApiModelProperty(example = "string@gmail.com")
    private final String email;
    @ApiModelProperty(example = "12345")
    private final String password;
    @ApiModelProperty(example = "Johnny")
    private final String firstName;
    @ApiModelProperty(example = "Bravo")
    private final String lastName;
}
