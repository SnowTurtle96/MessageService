package jamie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserMessageAccount.
 */
@Entity
@Table(name = "user_message_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserMessageAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "username")
    private String username;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "access_level")
    private Integer accessLevel;

    @OneToMany(mappedBy = "userMessageAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserMessagesSent> jobs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public UserMessageAccount firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public UserMessageAccount lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public UserMessageAccount dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public UserMessageAccount username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public UserMessageAccount password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public UserMessageAccount accessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
        return this;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Set<UserMessagesSent> getJobs() {
        return jobs;
    }

    public UserMessageAccount jobs(Set<UserMessagesSent> userMessagesSents) {
        this.jobs = userMessagesSents;
        return this;
    }

    public UserMessageAccount addJob(UserMessagesSent userMessagesSent) {
        this.jobs.add(userMessagesSent);
        userMessagesSent.setUserMessageAccount(this);
        return this;
    }

    public UserMessageAccount removeJob(UserMessagesSent userMessagesSent) {
        this.jobs.remove(userMessagesSent);
        userMessagesSent.setUserMessageAccount(null);
        return this;
    }

    public void setJobs(Set<UserMessagesSent> userMessagesSents) {
        this.jobs = userMessagesSents;
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
        UserMessageAccount userMessageAccount = (UserMessageAccount) o;
        if (userMessageAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMessageAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserMessageAccount{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", dob='" + getDob() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", accessLevel='" + getAccessLevel() + "'" +
            "}";
    }
}
