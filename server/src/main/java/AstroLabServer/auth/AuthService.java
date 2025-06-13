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
        int userId;

        try (PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO users(login, password_hash, salt) VALUES (?, ?, ?)")) {
            ps.setString(1, login);
            ps.setString(2, passwordHash);
            ps.setString(3, salt);
            ps.executeUpdate();
        }

        try (PreparedStatement ps = this.connection.prepareStatement(
                "SELECT id FROM users WHERE login = ?")) {
            ps.setString(1, login);
            try {
                ResultSet res = ps.executeQuery();
                res.next();
                userId = res.getInt("id");
            } catch (SQLException ignored){
                return AuthStates.CANT_LOGIN_ACCOUNT_NOT_REGISTERED;
            }
        }
        user.setUserId(userId);
        return AuthStates.REGISTERED;
    }

    public AuthStates login(User user) throws SQLException {
        String login = user.getLogin();
        String salt;
        String checkPassword;
        int userId;

        try (PreparedStatement ps = this.connection.prepareStatement(
                "SELECT login, salt, password_hash, id FROM users WHERE login = ?")) {
            ps.setString(1, login);
            try {
                ResultSet res = ps.executeQuery();
                res.next();
                res.getString("login");
                salt = res.getString("salt");
                checkPassword = res.getString("password_hash");
                userId = res.getInt("id");
            } catch (SQLException ignored){
                return AuthStates.CANT_LOGIN_ACCOUNT_NOT_REGISTERED;
            }
        }
        user.setUserId(userId);

        String password = user.getPassword();
        String passwordHash = PasswordSecurityUtils.hashPassword(password, salt);

        return checkPassword.equals(passwordHash) ? AuthStates.LOGGED : AuthStates.NOT_LOGGED;
    }
}

