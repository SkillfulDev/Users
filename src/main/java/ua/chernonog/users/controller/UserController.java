package ua.chernonog.users.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ua.chernonog.users.model.request.BirthdateRangeRequest;
import ua.chernonog.users.model.request.UserRequest;
import ua.chernonog.users.model.response.UserResponse;
import ua.chernonog.users.service.UserService;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@NoArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping("/users/register")
    public UserResponse registerUser(@RequestBody UserRequest userRequest){
        return userService.saveUser(userRequest);
    }

    @PutMapping("/users/update/{id}")
    public UserResponse registerUser(@RequestBody UserRequest userRequest ,
                                     @PathVariable("id") long id) {
     return userService.updateUser(id,userRequest);
    }

    @DeleteMapping("/users/delete/{id}")
    public String registerUser(@RequestBody @PathVariable("id") long id) {
        return userService.deleteUser(id);
    }
    @GetMapping("/users")
    public List<UserResponse> registerUser(@RequestBody BirthdateRangeRequest birthdateRangeRequest) {
        return userService.findAllValidUser(birthdateRangeRequest);
    }
}
