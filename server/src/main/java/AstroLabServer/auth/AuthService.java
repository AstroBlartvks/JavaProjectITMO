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



//package AstroLabServer.auth;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import AstroLab.auth.User;
//
//
//public class AuthService {
//    private static final String USER_EXIST_QUERY =
//            "SELECT login FROM users WHERE login = ?";
//    private static final String INSERT_USER_QUERY =
//            "INSERT INTO users(login, password_hash, salt) VALUES (?, ?, ?)";
//    private static final String USER_CREDENTIALS_QUERY =
//            "SELECT salt, password_hash FROM users WHERE login = ?";
//
//    private final Connection connection;
//
//    public AuthService(Connection connection) {
//        this.connection = connection;
//    }
//
//    public AuthStates register(User user) throws SQLException {
//        if (checkUserExists(user.getLogin())) {
//            return AuthStates.CANT_REGISTER_ACCOUNT_EXIST;
//        }
//
//        String salt = PasswordSecurityUtils.generateSalt();
//        String passwordHash = PasswordSecurityUtils.hashPassword(
//                user.getPassword(),
//                salt
//        );
//
//        try (PreparedStatement ps = connection.prepareStatement(INSERT_USER_QUERY)) {
//            ps.setString(1, user.getLogin());
//            ps.setString(2, passwordHash);
//            ps.setString(3, salt);
//            ps.executeUpdate();
//        }
//
//        return AuthStates.REGISTERED;
//    }
//
//    public AuthStates login(User user) throws SQLException {
//        String login = user.getLogin();
//        Credentials credentials = getCredentials(login);
//
//        if (credentials == null) {
//            return AuthStates.CANT_LOGIN_ACCOUNT_NOT_REGISTERED;
//        }
//
//        String inputHash = PasswordSecurityUtils.hashPassword(
//                user.getPassword(),
//                credentials.salt()
//        );
//
//        return credentials.passwordHash().equals(inputHash)
//                ? AuthStates.LOGGED
//                : AuthStates.NOT_LOGGED;
//    }
//
//    private boolean checkUserExists(String login) throws SQLException {
//        try (PreparedStatement ps = connection.prepareStatement(USER_EXIST_QUERY)) {
//            ps.setString(1, login);
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//        }
//    }
//
//    private Credentials getCredentials(String login) throws SQLException {
//        try (PreparedStatement ps = connection.prepareStatement(USER_CREDENTIALS_QUERY)) {
//            ps.setString(1, login);
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next()
//                        ? new Credentials(rs.getString("salt"), rs.getString("password_hash"))
//                        : null;
//            }
//        }
//    }
//
//    private record Credentials(String salt, String passwordHash) {}
//}