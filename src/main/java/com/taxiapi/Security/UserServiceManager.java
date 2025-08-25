package com.taxiapi.Security;

import com.taxiapi.Exception.UnauthorizedException;
import com.taxiapi.Model.User;
import com.taxiapi.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceManager implements AuthenticateUser{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsManagerService userDetailsManagerService;

    public UserServiceManager(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager,
                              JwtUtil jwtUtil,
                              UserDetailsManagerService userDetailsManagerService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsManagerService = userDetailsManagerService;
    }


    public User findUserById(Long id) {

        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(()->
                new UnauthorizedException("User not found."));
    }



    public void saveUser(User user) {
        String username = user.getUsername();

        if(userRepository.findByUsername(username).isEmpty()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

        }

    }


    @Override
    public AuthResponse createAuthentication(AuthRequest authRequest) {
        User user;
        try {
            user = userRepository.findByUsername
                    (authRequest.getUsername()).get();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest
                            .getUsername(), authRequest.getPassword())
            );
        } catch(Exception e){
            throw new UnauthorizedException(
                    "Incorrect username or password.");
        }

        final UserDetails userDetails = userDetailsManagerService
                .loadUserByUsername(authRequest.getUsername());

        final String jwt = jwtUtil
                .generateToken(userDetails.getUsername());

        return new AuthResponse(jwt);
    }
}
