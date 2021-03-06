package pl.polsl.io.mytoolyourtool.utils.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByEmail(username);

        if (user.isPresent()) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password(user.get().getPassword())
                    .authorities(user.get().getRoles())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }

}