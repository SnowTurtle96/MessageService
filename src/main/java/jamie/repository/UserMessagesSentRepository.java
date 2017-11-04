package jamie.repository;

import jamie.domain.UserMessagesSent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserMessagesSent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserMessagesSentRepository extends JpaRepository<UserMessagesSent, Long> {

}
