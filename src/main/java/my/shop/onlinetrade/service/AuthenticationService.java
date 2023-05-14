package my.shop.onlinetrade.service;

import my.shop.onlinetrade.security.AuthenticationRequest;
import my.shop.onlinetrade.security.AuthenticationResponse;
import my.shop.onlinetrade.security.RegisterAdminRequest;
import my.shop.onlinetrade.security.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse registerUser(RegisterRequest registerRequest);

    AuthenticationResponse authentication(AuthenticationRequest authenticationRequest);

    AuthenticationResponse registerAdmin(RegisterAdminRequest registerAdminRequest);


}
