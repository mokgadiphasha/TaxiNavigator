package com.taxiapi.Security;

import com.taxiapi.Exception.UnauthorizedException;
import com.taxiapi.Model.User;
import com.taxiapi.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsManagerService userDetailsManagerService;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       UserDetailsManagerService userDetailsManagerService,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsManagerService = userDetailsManagerService;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse createAuthentication(AuthRequest authRequest) {
        User user;
        try {
            user = userRepository.findByUsername(authRequest.getUsername()).get();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new UnauthorizedException(
                    "Username or password incorrect.");
        }

        final UserDetails userDetails = userDetailsManagerService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthResponse(jwt);
    }
}
