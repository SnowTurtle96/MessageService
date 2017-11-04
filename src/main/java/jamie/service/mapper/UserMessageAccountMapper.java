package jamie.service.mapper;

import jamie.domain.*;
import jamie.service.dto.UserMessageAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserMessageAccount and its DTO UserMessageAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserMessageAccountMapper extends EntityMapper<UserMessageAccountDTO, UserMessageAccount> {

    

    @Mapping(target = "jobs", ignore = true)
    UserMessageAccount toEntity(UserMessageAccountDTO userMessageAccountDTO);

    default UserMessageAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserMessageAccount userMessageAccount = new UserMessageAccount();
        userMessageAccount.setId(id);
        return userMessageAccount;
    }
}
