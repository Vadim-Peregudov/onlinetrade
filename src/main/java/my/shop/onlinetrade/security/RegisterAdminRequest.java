package my.shop.onlinetrade.security;

public class RegisterAdminRequest {
    private String login;
    private String password;

    public RegisterAdminRequest() {
    }

    public RegisterAdminRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
