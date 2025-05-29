package AstroLabServer.auth;

import AstroLab.auth.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    private final Connection connection;

    public AuthService(Connection connection){
        this.connection = connection;
    }

    public AuthStates register(User user) throws SQLException {
        String login = user.getLogin();
        try (PreparedStatement ps = this.connection.prepareStatement(
                "SELECT login FROM users WHERE login = ?")) {
            ps.setString(1, login);
            try {
                ResultSet res = ps.executeQuery();
                res.next();
                res.getString("login");
                return AuthStates.CANT_REGISTER_ACCOUNT_EXIST;
            } catch (SQLException ignored){}
        }

        String password = user.getPassword();

        String salt = PasswordSecurityUtils.generateSalt();
        String passwordHash = PasswordSecurityUtils.hashPassword(password, salt);

        try (PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO users(login, password_hash, salt) VALUES (?, ?, ?)")) {
            ps.setString(1, login);
            ps.setString(2, passwordHash);
            ps.setString(3, salt);
            ps.executeUpdate();
        }
        return AuthStates.REGISTERED;
    }

    public AuthStates login(User user) throws SQLException {
        String login = user.getLogin();
        String salt;
        String checkPassword;

        try (PreparedStatement ps = this.connection.prepareStatement(
                "SELECT login, salt, password_hash FROM users WHERE login = ?")) {
            ps.setString(1, login);
            try {
                ResultSet res = ps.executeQuery();
                res.next();
                res.getString("login");
                salt = res.getString("salt");
                checkPassword = res.getString("password_hash");
            } catch (SQLException ignored){
                return AuthStates.CANT_LOGIN_ACCOUNT_NOT_REGISTERED;
            }
        }

        String password = user.getPassword();
        String passwordHash = PasswordSecurityUtils.hashPassword(password, salt);

        return checkPassword.equals(passwordHash) ? AuthStates.LOGGED : AuthStates.NOT_LOGGED;
    }
}

