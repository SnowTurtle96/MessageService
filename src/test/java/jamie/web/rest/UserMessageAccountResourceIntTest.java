package jamie.web.rest;

import jamie.ChatapplicationApp;

import jamie.domain.UserMessageAccount;
import jamie.repository.UserMessageAccountRepository;
import jamie.service.dto.UserMessageAccountDTO;
import jamie.service.mapper.UserMessageAccountMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static jamie.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserMessageAccountResource REST controller.
 *
 * @see UserMessageAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatapplicationApp.class)
public class UserMessageAccountResourceIntTest {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACCESS_LEVEL = 1;
    private static final Integer UPDATED_ACCESS_LEVEL = 2;

    @Autowired
    private UserMessageAccountRepository userMessageAccountRepository;

    @Autowired
    private UserMessageAccountMapper userMessageAccountMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserMessageAccountMockMvc;

    private UserMessageAccount userMessageAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserMessageAccountResource userMessageAccountResource = new UserMessageAccountResource(userMessageAccountRepository, userMessageAccountMapper);
        this.restUserMessageAccountMockMvc = MockMvcBuilders.standaloneSetup(userMessageAccountResource)
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
    public static UserMessageAccount createEntity(EntityManager em) {
        UserMessageAccount userMessageAccount = new UserMessageAccount()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .dob(DEFAULT_DOB)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .accessLevel(DEFAULT_ACCESS_LEVEL);
        return userMessageAccount;
    }

    @Before
    public void initTest() {
        userMessageAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserMessageAccount() throws Exception {
        int databaseSizeBeforeCreate = userMessageAccountRepository.findAll().size();

        // Create the UserMessageAccount
        UserMessageAccountDTO userMessageAccountDTO = userMessageAccountMapper.toDto(userMessageAccount);
        restUserMessageAccountMockMvc.perform(post("/api/user-message-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMessageAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the UserMessageAccount in the database
        List<UserMessageAccount> userMessageAccountList = userMessageAccountRepository.findAll();
        assertThat(userMessageAccountList).hasSize(databaseSizeBeforeCreate + 1);
        UserMessageAccount testUserMessageAccount = userMessageAccountList.get(userMessageAccountList.size() - 1);
        assertThat(testUserMessageAccount.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testUserMessageAccount.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testUserMessageAccount.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testUserMessageAccount.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUserMessageAccount.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserMessageAccount.getAccessLevel()).isEqualTo(DEFAULT_ACCESS_LEVEL);
    }

    @Test
    @Transactional
    public void createUserMessageAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userMessageAccountRepository.findAll().size();

        // Create the UserMessageAccount with an existing ID
        userMessageAccount.setId(1L);
        UserMessageAccountDTO userMessageAccountDTO = userMessageAccountMapper.toDto(userMessageAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMessageAccountMockMvc.perform(post("/api/user-message-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMessageAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserMessageAccount in the database
        List<UserMessageAccount> userMessageAccountList = userMessageAccountRepository.findAll();
        assertThat(userMessageAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserMessageAccounts() throws Exception {
        // Initialize the database
        userMessageAccountRepository.saveAndFlush(userMessageAccount);

        // Get all the userMessageAccountList
        restUserMessageAccountMockMvc.perform(get("/api/user-message-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMessageAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].accessLevel").value(hasItem(DEFAULT_ACCESS_LEVEL)));
    }

    @Test
    @Transactional
    public void getUserMessageAccount() throws Exception {
        // Initialize the database
        userMessageAccountRepository.saveAndFlush(userMessageAccount);

        // Get the userMessageAccount
        restUserMessageAccountMockMvc.perform(get("/api/user-message-accounts/{id}", userMessageAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userMessageAccount.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.accessLevel").value(DEFAULT_ACCESS_LEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingUserMessageAccount() throws Exception {
        // Get the userMessageAccount
        restUserMessageAccountMockMvc.perform(get("/api/user-message-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserMessageAccount() throws Exception {
        // Initialize the database
        userMessageAccountRepository.saveAndFlush(userMessageAccount);
        int databaseSizeBeforeUpdate = userMessageAccountRepository.findAll().size();

        // Update the userMessageAccount
        UserMessageAccount updatedUserMessageAccount = userMessageAccountRepository.findOne(userMessageAccount.getId());
        updatedUserMessageAccount
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .dob(UPDATED_DOB)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .accessLevel(UPDATED_ACCESS_LEVEL);
        UserMessageAccountDTO userMessageAccountDTO = userMessageAccountMapper.toDto(updatedUserMessageAccount);

        restUserMessageAccountMockMvc.perform(put("/api/user-message-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMessageAccountDTO)))
            .andExpect(status().isOk());

        // Validate the UserMessageAccount in the database
        List<UserMessageAccount> userMessageAccountList = userMessageAccountRepository.findAll();
        assertThat(userMessageAccountList).hasSize(databaseSizeBeforeUpdate);
        UserMessageAccount testUserMessageAccount = userMessageAccountList.get(userMessageAccountList.size() - 1);
        assertThat(testUserMessageAccount.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testUserMessageAccount.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testUserMessageAccount.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testUserMessageAccount.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserMessageAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserMessageAccount.getAccessLevel()).isEqualTo(UPDATED_ACCESS_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingUserMessageAccount() throws Exception {
        int databaseSizeBeforeUpdate = userMessageAccountRepository.findAll().size();

        // Create the UserMessageAccount
        UserMessageAccountDTO userMessageAccountDTO = userMessageAccountMapper.toDto(userMessageAccount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserMessageAccountMockMvc.perform(put("/api/user-message-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMessageAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the UserMessageAccount in the database
        List<UserMessageAccount> userMessageAccountList = userMessageAccountRepository.findAll();
        assertThat(userMessageAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserMessageAccount() throws Exception {
        // Initialize the database
        userMessageAccountRepository.saveAndFlush(userMessageAccount);
        int databaseSizeBeforeDelete = userMessageAccountRepository.findAll().size();

        // Get the userMessageAccount
        restUserMessageAccountMockMvc.perform(delete("/api/user-message-accounts/{id}", userMessageAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserMessageAccount> userMessageAccountList = userMessageAccountRepository.findAll();
        assertThat(userMessageAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMessageAccount.class);
        UserMessageAccount userMessageAccount1 = new UserMessageAccount();
        userMessageAccount1.setId(1L);
        UserMessageAccount userMessageAccount2 = new UserMessageAccount();
        userMessageAccount2.setId(userMessageAccount1.getId());
        assertThat(userMessageAccount1).isEqualTo(userMessageAccount2);
        userMessageAccount2.setId(2L);
        assertThat(userMessageAccount1).isNotEqualTo(userMessageAccount2);
        userMessageAccount1.setId(null);
        assertThat(userMessageAccount1).isNotEqualTo(userMessageAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMessageAccountDTO.class);
        UserMessageAccountDTO userMessageAccountDTO1 = new UserMessageAccountDTO();
        userMessageAccountDTO1.setId(1L);
        UserMessageAccountDTO userMessageAccountDTO2 = new UserMessageAccountDTO();
        assertThat(userMessageAccountDTO1).isNotEqualTo(userMessageAccountDTO2);
        userMessageAccountDTO2.setId(userMessageAccountDTO1.getId());
        assertThat(userMessageAccountDTO1).isEqualTo(userMessageAccountDTO2);
        userMessageAccountDTO2.setId(2L);
        assertThat(userMessageAccountDTO1).isNotEqualTo(userMessageAccountDTO2);
        userMessageAccountDTO1.setId(null);
        assertThat(userMessageAccountDTO1).isNotEqualTo(userMessageAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userMessageAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userMessageAccountMapper.fromId(null)).isNull();
    }
}
