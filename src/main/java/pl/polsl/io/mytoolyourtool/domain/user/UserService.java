package pl.polsl.io.mytoolyourtool.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.LoginDTO;
import pl.polsl.io.mytoolyourtool.api.dto.SignUpDTO;
import pl.polsl.io.mytoolyourtool.api.dto.UserDetailsDTO;
import pl.polsl.io.mytoolyourtool.utils.exception.ObjectExistsException;
import pl.polsl.io.mytoolyourtool.utils.security.jwt.JwtTokenProvider;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public String login(LoginDTO credentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        return "Bearer " + jwtTokenProvider.createToken(credentials.getUsername(), userRepository.findByUsername(credentials.getUsername()).get().getRoles());
    }

    public User createUser(SignUpDTO signUpDTO) {
        if(userRepository.findByUsername(signUpDTO.getUsername()).isPresent())
            throw new ObjectExistsException("User with username '" + signUpDTO.getUsername() + "' already exists");

        Role role = roleRepository.findByName("ROLE_USER").orElse(roleRepository.save(new Role("ROLE_USER")));
        LinkedList<Role> roles = new LinkedList<>();
        roles.add(role);

        User user = User.builder()
                .username(signUpDTO.getUsername())
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .roles(roles)
                .build();
        return userRepository.save(user);
    }

    public UserDetailsDTO getUserDetails() {
        User user = whoami();
        return new UserDetailsDTO(user.getUsername(), user.getFirstName(), user.getLastName());
    }

    public User whoami(){
        return search(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public User search(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User with that Username doesn't exist"));
    }

}
