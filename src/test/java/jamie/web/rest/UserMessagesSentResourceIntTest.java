package jamie.web.rest;

import jamie.ChatapplicationApp;

import jamie.domain.UserMessagesSent;
import jamie.repository.UserMessagesSentRepository;
import jamie.service.dto.UserMessagesSentDTO;
import jamie.service.mapper.UserMessagesSentMapper;
import jamie.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static jamie.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserMessagesSentResource REST controller.
 *
 * @see UserMessagesSentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatapplicationApp.class)
public class UserMessagesSentResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIME_SENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_SENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    @Autowired
    private UserMessagesSentRepository userMessagesSentRepository;

    @Autowired
    private UserMessagesSentMapper userMessagesSentMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserMessagesSentMockMvc;

    private UserMessagesSent userMessagesSent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserMessagesSentResource userMessagesSentResource = new UserMessagesSentResource(userMessagesSentRepository, userMessagesSentMapper);
        this.restUserMessagesSentMockMvc = MockMvcBuilders.standaloneSetup(userMessagesSentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserMessagesSent createEntity(EntityManager em) {
        UserMessagesSent userMessagesSent = new UserMessagesSent()
            .username(DEFAULT_USERNAME)
            .timeSent(DEFAULT_TIME_SENT)
            .body(DEFAULT_BODY);
        return userMessagesSent;
    }

    @Before
    public void initTest() {
        userMessagesSent = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserMessagesSent() throws Exception {
        int databaseSizeBeforeCreate = userMessagesSentRepository.findAll().size();

        // Create the UserMessagesSent
        UserMessagesSentDTO userMessagesSentDTO = userMessagesSentMapper.toDto(userMessagesSent);
        restUserMessagesSentMockMvc.perform(post("/api/user-messages-sents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMessagesSentDTO)))
            .andExpect(status().isCreated());

        // Validate the UserMessagesSent in the database
        List<UserMessagesSent> userMessagesSentList = userMessagesSentRepository.findAll();
        assertThat(userMessagesSentList).hasSize(databaseSizeBeforeCreate + 1);
        UserMessagesSent testUserMessagesSent = userMessagesSentList.get(userMessagesSentList.size() - 1);
        assertThat(testUserMessagesSent.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUserMessagesSent.getTimeSent()).isEqualTo(DEFAULT_TIME_SENT);
        assertThat(testUserMessagesSent.getBody()).isEqualTo(DEFAULT_BODY);
    }

    @Test
    @Transactional
    public void createUserMessagesSentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userMessagesSentRepository.findAll().size();

        // Create the UserMessagesSent with an existing ID
        userMessagesSent.setId(1L);
        UserMessagesSentDTO userMessagesSentDTO = userMessagesSentMapper.toDto(userMessagesSent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMessagesSentMockMvc.perform(post("/api/user-messages-sents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMessagesSentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserMessagesSent in the database
        List<UserMessagesSent> userMessagesSentList = userMessagesSentRepository.findAll();
        assertThat(userMessagesSentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserMessagesSents() throws Exception {
        // Initialize the database
        userMessagesSentRepository.saveAndFlush(userMessagesSent);

        // Get all the userMessagesSentList
        restUserMessagesSentMockMvc.perform(get("/api/user-messages-sents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMessagesSent.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].timeSent").value(hasItem(DEFAULT_TIME_SENT.toString())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())));
    }

    @Test
    @Transactional
    public void getUserMessagesSent() throws Exception {
        // Initialize the database
        userMessagesSentRepository.saveAndFlush(userMessagesSent);

        // Get the userMessagesSent
        restUserMessagesSentMockMvc.perform(get("/api/user-messages-sents/{id}", userMessagesSent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userMessagesSent.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.timeSent").value(DEFAULT_TIME_SENT.toString()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserMessagesSent() throws Exception {
        // Get the userMessagesSent
        restUserMessagesSentMockMvc.perform(get("/api/user-messages-sents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserMessagesSent() throws Exception {
        // Initialize the database
        userMessagesSentRepository.saveAndFlush(userMessagesSent);
        int databaseSizeBeforeUpdate = userMessagesSentRepository.findAll().size();

        // Update the userMessagesSent
        UserMessagesSent updatedUserMessagesSent = userMessagesSentRepository.findOne(userMessagesSent.getId());
        updatedUserMessagesSent
            .username(UPDATED_USERNAME)
            .timeSent(UPDATED_TIME_SENT)
            .body(UPDATED_BODY);
        UserMessagesSentDTO userMessagesSentDTO = userMessagesSentMapper.toDto(updatedUserMessagesSent);

        restUserMessagesSentMockMvc.perform(put("/api/user-messages-sents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMessagesSentDTO)))
            .andExpect(status().isOk());

        // Validate the UserMessagesSent in the database
        List<UserMessagesSent> userMessagesSentList = userMessagesSentRepository.findAll();
        assertThat(userMessagesSentList).hasSize(databaseSizeBeforeUpdate);
        UserMessagesSent testUserMessagesSent = userMessagesSentList.get(userMessagesSentList.size() - 1);
        assertThat(testUserMessagesSent.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserMessagesSent.getTimeSent()).isEqualTo(UPDATED_TIME_SENT);
        assertThat(testUserMessagesSent.getBody()).isEqualTo(UPDATED_BODY);
    }

    @Test
    @Transactional
    public void updateNonExistingUserMessagesSent() throws Exception {
        int databaseSizeBeforeUpdate = userMessagesSentRepository.findAll().size();

        // Create the UserMessagesSent
        UserMessagesSentDTO userMessagesSentDTO = userMessagesSentMapper.toDto(userMessagesSent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserMessagesSentMockMvc.perform(put("/api/user-messages-sents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMessagesSentDTO)))
            .andExpect(status().isCreated());

        // Validate the UserMessagesSent in the database
        List<UserMessagesSent> userMessagesSentList = userMessagesSentRepository.findAll();
        assertThat(userMessagesSentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserMessagesSent() throws Exception {
        // Initialize the database
        userMessagesSentRepository.saveAndFlush(userMessagesSent);
        int databaseSizeBeforeDelete = userMessagesSentRepository.findAll().size();

        // Get the userMessagesSent
        restUserMessagesSentMockMvc.perform(delete("/api/user-messages-sents/{id}", userMessagesSent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserMessagesSent> userMessagesSentList = userMessagesSentRepository.findAll();
        assertThat(userMessagesSentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMessagesSent.class);
        UserMessagesSent userMessagesSent1 = new UserMessagesSent();
        userMessagesSent1.setId(1L);
        UserMessagesSent userMessagesSent2 = new UserMessagesSent();
        userMessagesSent2.setId(userMessagesSent1.getId());
        assertThat(userMessagesSent1).isEqualTo(userMessagesSent2);
        userMessagesSent2.setId(2L);
        assertThat(userMessagesSent1).isNotEqualTo(userMessagesSent2);
        userMessagesSent1.setId(null);
        assertThat(userMessagesSent1).isNotEqualTo(userMessagesSent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMessagesSentDTO.class);
        UserMessagesSentDTO userMessagesSentDTO1 = new UserMessagesSentDTO();
        userMessagesSentDTO1.setId(1L);
        UserMessagesSentDTO userMessagesSentDTO2 = new UserMessagesSentDTO();
        assertThat(userMessagesSentDTO1).isNotEqualTo(userMessagesSentDTO2);
        userMessagesSentDTO2.setId(userMessagesSentDTO1.getId());
        assertThat(userMessagesSentDTO1).isEqualTo(userMessagesSentDTO2);
        userMessagesSentDTO2.setId(2L);
        assertThat(userMessagesSentDTO1).isNotEqualTo(userMessagesSentDTO2);
        userMessagesSentDTO1.setId(null);
        assertThat(userMessagesSentDTO1).isNotEqualTo(userMessagesSentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userMessagesSentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userMessagesSentMapper.fromId(null)).isNull();
    }
}
