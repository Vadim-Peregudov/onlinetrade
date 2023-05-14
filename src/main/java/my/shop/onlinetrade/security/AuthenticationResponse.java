package my.shop.onlinetrade.security;

import my.shop.onlinetrade.entity.Role;

import java.util.List;

public class AuthenticationResponse {

    private String token;
    private List<Role> roles;


    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse(String token, List<Role> roles) {
        this.token = token;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
