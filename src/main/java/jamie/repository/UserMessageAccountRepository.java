package jamie.repository;

import jamie.domain.UserMessageAccount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserMessageAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserMessageAccountRepository extends JpaRepository<UserMessageAccount, Long> {

}
