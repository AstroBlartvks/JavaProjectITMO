package AstroLab.auth;

import lombok.Getter;

@Getter
public class User {
    protected final String login;
    protected final String password;
    protected final String salt;

    public User(String login, String passwordHash, String salt) {
        this.login = login;
        this.password = passwordHash;
        this.salt = salt;
    }

}