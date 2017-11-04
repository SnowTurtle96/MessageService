package jamie.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserMessagesSent entity.
 */
public class UserMessagesSentDTO implements Serializable {

    private Long id;

    private String username;

    private Instant timeSent;

    private String body;

    private Long userMessageAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Instant timeSent) {
        this.timeSent = timeSent;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getUserMessageAccountId() {
        return userMessageAccountId;
    }

    public void setUserMessageAccountId(Long userMessageAccountId) {
        this.userMessageAccountId = userMessageAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserMessagesSentDTO userMessagesSentDTO = (UserMessagesSentDTO) o;
        if(userMessagesSentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMessagesSentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserMessagesSentDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", timeSent='" + getTimeSent() + "'" +
            ", body='" + getBody() + "'" +
            "}";
    }
}
