package com.taxiapi.Security;


public interface AuthenticateUser {
    AuthResponse createAuthentication(AuthRequest authRequest);
}
