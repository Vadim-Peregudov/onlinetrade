package my.shop.onlinetrade.service.impl;

import my.shop.onlinetrade.entity.Customers;
import my.shop.onlinetrade.entity.Role;
import my.shop.onlinetrade.entity.User;
import my.shop.onlinetrade.repository.ClientRepository;
import my.shop.onlinetrade.repository.UserRepository;
import my.shop.onlinetrade.security.*;
import my.shop.onlinetrade.service.AuthenticationService;
import my.shop.onlinetrade.service.JwtService;
import my.shop.onlinetrade.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder, RoleService roleService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse registerUser(RegisterRequest registerRequest) {
        Role role = roleService.getRoleByName("ROLE_USER");

        User user = new User(registerRequest.getLogin()
                , passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(new ArrayList<>() {{
            add(role);
        }});

        Customers customers = new Customers(
                registerRequest.getFirstName()
                , registerRequest.getLastName()
                , registerRequest.getLogin()
                , registerRequest.getPhone()
                , user
        );

        userRepository.save(user);
        clientRepository.save(customers);

        UserDetails userDetails = new UserDetailsImpl(user);

        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getLogin(),
                        authenticationRequest.getPassword()
                )
        );
        User user = userRepository.findByLogin(authenticationRequest.getLogin())
                .orElseThrow(() -> new RuntimeException("no user"));
        UserDetails userDetails = new UserDetailsImpl(user);
        String jwtToken = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwtToken, user.getRoles());
    }

    @Override
    public AuthenticationResponse registerAdmin(RegisterAdminRequest registerAdminRequest) {
        Role role = roleService.getRoleByName("ROLE_ADMIN");

        User user = new User(registerAdminRequest.getLogin()
                , passwordEncoder.encode(registerAdminRequest.getPassword()));
        user.setRoles(new ArrayList<>() {{
            add(role);
        }});

        userRepository.save(user);

        UserDetails userDetails = new UserDetailsImpl(user);

        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthenticationResponse(jwtToken, user.getRoles());
    }


}
