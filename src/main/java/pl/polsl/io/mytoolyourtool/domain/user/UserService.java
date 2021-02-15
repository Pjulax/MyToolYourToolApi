package pl.polsl.io.mytoolyourtool.domain.user;

import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.utils.exception.ObjectExistsException;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void addNewUser(User user)
    {
        if (null==user.getUsername() || null==user.getPassword() || user.getUsername().isEmpty() || user.getPassword().isEmpty())
        {
            //JAKIS WYJON KURDE BELE TUTAJ BO NULL JAKIS
            System.out.println("dupa");
            return;
        }
        if(!userRepository.existsByUsername(user.getUsername()))
        {
            userRepository.save(user);
        }
        else
        {
            throw new ObjectExistsException("User with username '" + user.getUsername() + "' already exists.");
        }
    }


    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users;
    }
}
