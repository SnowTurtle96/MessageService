package jamie.web.rest;

import com.codahale.metrics.annotation.Timed;
import jamie.domain.UserMessagesSent;

import jamie.repository.UserMessagesSentRepository;
import jamie.web.rest.errors.BadRequestAlertException;
import jamie.web.rest.util.HeaderUtil;
import jamie.service.dto.UserMessagesSentDTO;
import jamie.service.mapper.UserMessagesSentMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserMessagesSent.
 */
@RestController
@RequestMapping("/api")
public class UserMessagesSentResource {

    private final Logger log = LoggerFactory.getLogger(UserMessagesSentResource.class);

    private static final String ENTITY_NAME = "userMessagesSent";

    private final UserMessagesSentRepository userMessagesSentRepository;

    private final UserMessagesSentMapper userMessagesSentMapper;

    public UserMessagesSentResource(UserMessagesSentRepository userMessagesSentRepository, UserMessagesSentMapper userMessagesSentMapper) {
        this.userMessagesSentRepository = userMessagesSentRepository;
        this.userMessagesSentMapper = userMessagesSentMapper;
    }

    /**
     * POST  /user-messages-sents : Create a new userMessagesSent.
     *
     * @param userMessagesSentDTO the userMessagesSentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userMessagesSentDTO, or with status 400 (Bad Request) if the userMessagesSent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-messages-sents")
    @Timed
    public ResponseEntity<UserMessagesSentDTO> createUserMessagesSent(@RequestBody UserMessagesSentDTO userMessagesSentDTO) throws URISyntaxException {
        System.out.println("Activated!");
        log.debug("REST request to save UserMessagesSent : {}", userMessagesSentDTO);
        if (userMessagesSentDTO.getId() != null) {
            throw new BadRequestAlertException("A new userMessagesSent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserMessagesSent userMessagesSent = userMessagesSentMapper.toEntity(userMessagesSentDTO);
        Instant nowTime = Instant.now();
        userMessagesSentDTO.setTimeSent(nowTime);
        userMessagesSent = userMessagesSentRepository.save(userMessagesSent);
        UserMessagesSentDTO result = userMessagesSentMapper.toDto(userMessagesSent);
        return ResponseEntity.created(new URI("/api/user-messages-sents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-messages-sents : Updates an existing userMessagesSent.
     *
     * @param userMessagesSentDTO the userMessagesSentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userMessagesSentDTO,
     * or with status 400 (Bad Request) if the userMessagesSentDTO is not valid,
     * or with status 500 (Internal Server Error) if the userMessagesSentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-messages-sents")
    @Timed
    public ResponseEntity<UserMessagesSentDTO> updateUserMessagesSent(@RequestBody UserMessagesSentDTO userMessagesSentDTO) throws URISyntaxException {
        log.debug("REST request to update UserMessagesSent : {}", userMessagesSentDTO);
        if (userMessagesSentDTO.getId() == null) {
            return createUserMessagesSent(userMessagesSentDTO);
        }
        UserMessagesSent userMessagesSent = userMessagesSentMapper.toEntity(userMessagesSentDTO);
        userMessagesSent = userMessagesSentRepository.save(userMessagesSent);
        UserMessagesSentDTO result = userMessagesSentMapper.toDto(userMessagesSent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userMessagesSentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-messages-sents : get all the userMessagesSents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userMessagesSents in body
     */
    @GetMapping("/user-messages-sents")
    @Timed
    public List<UserMessagesSentDTO> getAllUserMessagesSents() {
        log.debug("REST request to get all UserMessagesSents");
        List<UserMessagesSent> userMessagesSents = userMessagesSentRepository.findAll();

        return userMessagesSentMapper.toDto(userMessagesSents);

    }
    /**
     * GET  /user-messages-sents/:id : get the "id" userMessagesSent.
     *
     * @param id the id of the userMessagesSentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userMessagesSentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-messages-sents/{id}")
    @Timed
    public ResponseEntity<UserMessagesSentDTO> getUserMessagesSent(@PathVariable Long id) {
        log.debug("REST request to get UserMessagesSent : {}", id);
        UserMessagesSent userMessagesSent = userMessagesSentRepository.findOne(id);
        UserMessagesSentDTO userMessagesSentDTO = userMessagesSentMapper.toDto(userMessagesSent);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userMessagesSentDTO));
    }

    /**
     * DELETE  /user-messages-sents/:id : delete the "id" userMessagesSent.
     *
     * @param id the id of the userMessagesSentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-messages-sents/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserMessagesSent(@PathVariable Long id) {
        log.debug("REST request to delete UserMessagesSent : {}", id);
        userMessagesSentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
