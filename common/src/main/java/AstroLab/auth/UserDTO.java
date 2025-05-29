package AstroLab.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserDTO extends User{
    private final ConnectionType connectionType;

    public UserDTO(@JsonProperty("login") String login,
                   @JsonProperty("passwordHash") String passwordHash,
                   @JsonProperty("salt") String salt,
                   @JsonProperty("connectionType") ConnectionType connectionType) {
        super(login, passwordHash, salt);
        this.connectionType = connectionType;
    }

    @Override
    public String toString() {
        return "User{login:" + this.login + ";password:" + this.password +
                ";salt:" + this.salt + ";connectionType" + this.connectionType + ";}";
    }
}
