package ua.chernonog.users.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.chernonog.users.repository.UserRepository;

@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;

}
