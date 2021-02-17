package pl.polsl.io.mytoolyourtool.domain.user;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="client")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @OneToMany
    private List<Role> roles;
}
