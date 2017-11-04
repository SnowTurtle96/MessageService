package jamie.service.mapper;

import jamie.domain.*;
import jamie.service.dto.UserMessagesSentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserMessagesSent and its DTO UserMessagesSentDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMessageAccountMapper.class})
public interface UserMessagesSentMapper extends EntityMapper<UserMessagesSentDTO, UserMessagesSent> {

    @Mapping(source = "userMessageAccount.id", target = "userMessageAccountId")
    UserMessagesSentDTO toDto(UserMessagesSent userMessagesSent); 

    @Mapping(source = "userMessageAccountId", target = "userMessageAccount")
    UserMessagesSent toEntity(UserMessagesSentDTO userMessagesSentDTO);

    default UserMessagesSent fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserMessagesSent userMessagesSent = new UserMessagesSent();
        userMessagesSent.setId(id);
        return userMessagesSent;
    }
}
