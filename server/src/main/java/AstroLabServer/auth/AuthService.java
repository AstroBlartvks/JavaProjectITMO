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
                "INSERT INTO users(login, password_hash, salt) VALUES (?, ?, ?) RETURNING id")) {
            ps.setString(1, login);
            ps.setString(2, passwordHash);
            ps.setString(3, salt);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int userId = rs.getInt("id");
            user.setUserId(userId);
        }
        return AuthStates.REGISTERED;
    }

    public AuthStates login(User user) throws SQLException {
        String login = user.getLogin();
        String salt;
        String checkPassword;
        int userId;

        try (PreparedStatement ps = this.connection.prepareStatement(
                "SELECT id, salt, password_hash FROM users WHERE login = ?")) {
            ps.setString(1, login);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    userId = res.getInt("id");
                    salt = res.getString("salt");
                    checkPassword = res.getString("password_hash");

                    user.setUserId(userId);
                    String passwordHash = PasswordSecurityUtils.hashPassword(user.getPassword(), salt);

                    return checkPassword.equals(passwordHash) ?
                            AuthStates.LOGGED :
                            AuthStates.NOT_LOGGED;
                } else {
                    return AuthStates.CANT_LOGIN_ACCOUNT_NOT_REGISTERED;
                }
            }
        }
    }
}

