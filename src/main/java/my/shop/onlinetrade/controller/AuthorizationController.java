package my.shop.onlinetrade.controller;


import my.shop.onlinetrade.security.AuthenticationRequest;
import my.shop.onlinetrade.security.AuthenticationResponse;
import my.shop.onlinetrade.security.RegisterAdminRequest;
import my.shop.onlinetrade.security.RegisterRequest;
import my.shop.onlinetrade.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class AuthorizationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthorizationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "auth/authenticate", produces = "application/json;charset=UTF-8")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authenticationService.authentication(authenticationRequest));
    }

    @PostMapping(value = "auth/registration", produces = "application/json;charset=UTF-8")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(authenticationService.registerUser(registerRequest));
    }

    @PostMapping(value = "admin/registration",produces = "application/json;charset=UTF-8")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterAdminRequest registerAdminRequest
    ){
        return ResponseEntity.ok(authenticationService.registerAdmin(registerAdminRequest));
    }


}
