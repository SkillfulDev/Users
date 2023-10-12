package ua.chernonog.users.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ua.chernonog.users.exception.EmailFormatException;
import ua.chernonog.users.model.request.BirthdateRangeRequest;
import ua.chernonog.users.model.request.UserRequest;
import ua.chernonog.users.model.response.UserResponse;
import ua.chernonog.users.repository.UserRepository;
import ua.chernonog.users.service.UserService;


import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
    UserRepository userRepository;

    @PostMapping("/register")

    public UserResponse registerUser(@RequestBody UserRequest userRequest) {
        if (!userRequest.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new EmailFormatException();
        }
        return userService.saveUser(userRequest);
    }

    @PutMapping("/update/{id}")
    public UserResponse registerUser(@RequestBody UserRequest userRequest,
                                     @PathVariable("id") long id) {
             return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/delete/{id}")
    public String registerUser(@RequestBody @PathVariable("id") long id) {
        return userService.deleteUser(id);
    }

    @PostMapping()
    public List<UserResponse> registerUser(@RequestBody BirthdateRangeRequest birthdateRangeRequest) {
        return userService.findAllValidUser(birthdateRangeRequest);
    }

    @GetMapping("/allUsers")
    public List<UserResponse> registerUser() {
        return userService.findAllUsers();
    }
}

