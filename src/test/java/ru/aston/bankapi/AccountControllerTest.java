package ru.aston.bankapi;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.aston.bankapi.controller.AccountController;
import ru.aston.bankapi.dto.CreateAccountDto;
import ru.aston.bankapi.model.Account;
import ru.aston.bankapi.service.AccountService;
import ru.aston.bankapi.service.TransactionHistoryService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AccountController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@DisplayName("AccountControllerTest")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountControllerTest {
    public static final Account ARTHUR = Account.builder()
            .number(1000000)
            .beneficiaryName("Arthur")
            .isActive(true)
            .balance(500)
            .pinCodeHash("$2a$10$lo/YBKIbnC6LXA0hEyO.2OO5R0YekacHjjMSxJlYANxbRTRfCM5Wm")
            .build();

    public static final Account PARTNER = Account.builder()
            .number(1000001)
            .beneficiaryName("Partner")
            .isActive(true)
            .balance(1500)
            .pinCodeHash("$2a$10$GayoF9FQqKOiPnuPlq5XG.MmrYRRxHtL1d.NaO3pqypeX65cw8Lla")
            .build();

    public static final Account NEW_OWNER = Account.builder()
            .number(1000003)
            .beneficiaryName("New Owner")
            .isActive(true)
            .balance(0)
            .pinCodeHash("$2a$10$GayoF9FQqKOiPnuPlq5XG.MmrYRRxHtL1d.NaO3pqypeX65cw8Lla")
            .build();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionHistoryService transactionHistoryService;

    @BeforeEach
    void setUp() {
        when(accountService.getAccountByNumber(1000000)).thenReturn(ARTHUR);
        when(accountService.getAccountByNumber(1000001)).thenReturn(PARTNER);
        when(accountService.getAllAccountsWithHistory(null)).thenReturn(List.of(ARTHUR, PARTNER));
        when(accountService.createAccount(new CreateAccountDto("New Owner", 1111))).thenReturn(NEW_OWNER);
    }

    @Test
    public void return_account_with_number_1000000() throws Exception {
        mockMvc.perform(get("/account/1000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("number", is(1000000)))
                .andExpect(jsonPath("beneficiaryName", is("Arthur")));
    }

    @Test
    public void return_account_with_number_1000001() throws Exception {
        mockMvc.perform(get("/account/1000001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("number", is(1000001)))
                .andExpect(jsonPath("beneficiaryName", is("Partner")));
    }

    @Test
    public void return_all_accounts() throws Exception {
        mockMvc.perform(get("/account/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].number", is(1000000)))
                .andExpect(jsonPath("$.[1].number", is(1000001)));

    }

    @Test
    public void add_account() throws Exception {
        mockMvc.perform(post("/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"beneficiaryName\": \"New Owner\",\n" +
                                "  \"pinCode\": 1111\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("number", is(1000003)));
    }

}