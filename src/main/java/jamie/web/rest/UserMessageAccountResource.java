package jamie.web.rest;

import com.codahale.metrics.annotation.Timed;
import jamie.domain.UserMessageAccount;

import jamie.repository.UserMessageAccountRepository;
import jamie.service.mapper.UserMessageAccountMapper;
import jamie.web.rest.errors.BadRequestAlertException;
import jamie.web.rest.util.HeaderUtil;
import jamie.service.dto.UserMessageAccountDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserMessageAccount.
 */
@RestController
@RequestMapping("/api")
public class UserMessageAccountResource {

    private final Logger log = LoggerFactory.getLogger(UserMessageAccountResource.class);

    private static final String ENTITY_NAME = "userMessageAccount";

    private final UserMessageAccountRepository userMessageAccountRepository;

    private final UserMessageAccountMapper userMessageAccountMapper;

    public UserMessageAccountResource(UserMessageAccountRepository userMessageAccountRepository, UserMessageAccountMapper userMessageAccountMapper) {
        this.userMessageAccountRepository = userMessageAccountRepository;
        this.userMessageAccountMapper = userMessageAccountMapper;
    }

    /**
     * POST  /user-message-accounts : Create a new userMessageAccount.
     *
     * @param userMessageAccountDTO the userMessageAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userMessageAccountDTO, or with status 400 (Bad Request) if the userMessageAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-message-accounts")
    @Timed
    public ResponseEntity<UserMessageAccountDTO> createUserMessageAccount(@RequestBody UserMessageAccountDTO userMessageAccountDTO) throws URISyntaxException {
        log.debug("REST request to save UserMessageAccount : {}", userMessageAccountDTO);
        if (userMessageAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new userMessageAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserMessageAccount userMessageAccount = userMessageAccountMapper.toEntity(userMessageAccountDTO);
        userMessageAccount = userMessageAccountRepository.save(userMessageAccount);
        UserMessageAccountDTO result = userMessageAccountMapper.toDto(userMessageAccount);
        return ResponseEntity.created(new URI("/api/user-message-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-message-accounts : Updates an existing userMessageAccount.
     *
     * @param userMessageAccountDTO the userMessageAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userMessageAccountDTO,
     * or with status 400 (Bad Request) if the userMessageAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the userMessageAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-message-accounts")
    @Timed
    public ResponseEntity<UserMessageAccountDTO> updateUserMessageAccount(@RequestBody UserMessageAccountDTO userMessageAccountDTO) throws URISyntaxException {
        log.debug("REST request to update UserMessageAccount : {}", userMessageAccountDTO);
        if (userMessageAccountDTO.getId() == null) {
            return createUserMessageAccount(userMessageAccountDTO);
        }
        UserMessageAccount userMessageAccount = userMessageAccountMapper.toEntity(userMessageAccountDTO);
        userMessageAccount = userMessageAccountRepository.save(userMessageAccount);
        UserMessageAccountDTO result = userMessageAccountMapper.toDto(userMessageAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userMessageAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-message-accounts : get all the userMessageAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userMessageAccounts in body
     */
    @GetMapping("/user-message-accounts")
    @Timed
    public List<UserMessageAccountDTO> getAllUserMessageAccounts() {
        log.debug("REST request to get all UserMessageAccounts");
        List<UserMessageAccount> userMessageAccounts = userMessageAccountRepository.findAll();
        return userMessageAccountMapper.toDto(userMessageAccounts);
        }

    /**
     * GET  /user-message-accounts/:id : get the "id" userMessageAccount.
     *
     * @param id the id of the userMessageAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userMessageAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-message-accounts/{id}")
    @Timed
    public ResponseEntity<UserMessageAccountDTO> getUserMessageAccount(@PathVariable Long id) {
        log.debug("REST request to get UserMessageAccount : {}", id);
        UserMessageAccount userMessageAccount = userMessageAccountRepository.findOne(id);
        UserMessageAccountDTO userMessageAccountDTO = userMessageAccountMapper.toDto(userMessageAccount);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userMessageAccountDTO));
    }

    /**
     * DELETE  /user-message-accounts/:id : delete the "id" userMessageAccount.
     *
     * @param id the id of the userMessageAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-message-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserMessageAccount(@PathVariable Long id) {
        log.debug("REST request to delete UserMessageAccount : {}", id);
        userMessageAccountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
