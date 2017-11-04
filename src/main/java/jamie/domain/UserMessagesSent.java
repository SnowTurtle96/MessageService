package jamie.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A UserMessagesSent.
 */
@Entity
@Table(name = "user_messages_sent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserMessagesSent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "time_sent")
    private Instant timeSent;

    @Column(name = "jhi_body")
    private String body;

    @ManyToOne
    private UserMessageAccount userMessageAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public UserMessagesSent username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getTimeSent() {
        return timeSent;
    }

    public UserMessagesSent timeSent(Instant timeSent) {
        this.timeSent = timeSent;
        return this;
    }

    public void setTimeSent(Instant now) {
        this.timeSent = Instant.now();

    }

    public String getBody() {
        return body;
    }

    public UserMessagesSent body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserMessageAccount getUserMessageAccount() {
        return userMessageAccount;
    }

    public UserMessagesSent userMessageAccount(UserMessageAccount userMessageAccount) {
        this.userMessageAccount = userMessageAccount;
        return this;
    }

    public void setUserMessageAccount(UserMessageAccount userMessageAccount) {
        this.userMessageAccount = userMessageAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserMessagesSent userMessagesSent = (UserMessagesSent) o;
        if (userMessagesSent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMessagesSent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {

        return "UserMessagesSent{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", timeSent='" + getTimeSent() + "'" +
            ", body='" + getBody() + "'" +
            "}";
    }
}
