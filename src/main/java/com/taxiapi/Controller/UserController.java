package com.taxiapi.Controller;

import com.taxiapi.Model.User;
import com.taxiapi.Security.AuthRequest;
import com.taxiapi.Security.AuthResponse;
import com.taxiapi.Security.AuthenticateUser;
import com.taxiapi.Security.UserServiceManager;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class UserController {
    private final UserServiceManager UserServiceManager;
    private final AuthenticateUser authenticateUserManager;

    public UserController(UserServiceManager userServiceManager,
                          AuthenticateUser authenticateUser) {
        UserServiceManager = userServiceManager;
        authenticateUserManager = authenticateUser;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@Valid @RequestBody User user){
        UserServiceManager.saveUser(user);
    }


    @GetMapping("/{id}")
    public User returnUser(@PathVariable Long id){
        return UserServiceManager.findUserById(id);
    }


    @PostMapping("/login")
    public AuthResponse createAuthentication (@Valid @RequestBody AuthRequest request){
        return authenticateUserManager.createAuthentication(request);
    }


}
