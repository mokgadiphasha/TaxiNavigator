package com.taxiapi.Security;

//import com.example.ExpenseTracker.Model.User;
//import com.example.ExpenseTracker.Repository.UserRepository;
//import com.taxiapi.Model.User;
import com.taxiapi.Exception.UnauthorizedException;
import com.taxiapi.Model.User;
import com.taxiapi.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsManagerService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsManagerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException
                        ("Username or password incorrect."));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(), new ArrayList<>());
    }
}
