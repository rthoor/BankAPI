package ru.aston.bankapi;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.aston.bankapi.controller.TransactionController;
import ru.aston.bankapi.dto.DepositDto;
import ru.aston.bankapi.dto.TransferDto;
import ru.aston.bankapi.dto.WithdrawDto;
import ru.aston.bankapi.exception.AccountException;
import ru.aston.bankapi.exception.BalanceException;
import ru.aston.bankapi.exception.PinCodeException;
import ru.aston.bankapi.service.AccountService;
import ru.aston.bankapi.service.TransactionHistoryService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = TransactionController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@DisplayName("TransactionControllerTest")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionHistoryService transactionHistoryService;

    @BeforeEach
    void setUp() {
        when(accountService.deposit(new DepositDto(1000001, 2000))).thenReturn(AccountControllerTest.PARTNER);
        when(accountService.transfer(new TransferDto(1000001, 1000000, 1111, 500))).thenReturn(AccountControllerTest.PARTNER);
        when(accountService.withdraw(new WithdrawDto(1000001, 1111, 1000))).thenReturn(AccountControllerTest.PARTNER);
        when(accountService.withdraw(new WithdrawDto(1000000, 1234, 1000))).thenThrow(BalanceException.class);
        when(accountService.withdraw(new WithdrawDto(1000000, 1111, 1000))).thenThrow(PinCodeException.class);
        when(accountService.deposit(new DepositDto(1000004, 1000))).thenThrow(AccountException.class);
    }

    @Test
    public void success_deposit() throws Exception {
        mockMvc.perform(post("/transaction/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"number\": 1000001,\n" +
                                "  \"amount\": 2000\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("number", is(1000001)));
    }

    @Test
    public void failed_deposit_account_not_found() throws Exception {
        mockMvc.perform(post("/transaction/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"number\": 1000004,\n" +
                                "  \"amount\": 1000\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void success_withdraw() throws Exception {
        mockMvc.perform(post("/transaction/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"number\": 1000001,\n" +
                                "  \"pinCode\": 1111,\n" +
                                "  \"amount\": 1000\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("number", is(1000001)));
    }

    @Test
    public void failed_withdraw_not_enough_balance() throws Exception {
        mockMvc.perform(post("/transaction/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"number\": 1000000,\n" +
                                "  \"pinCode\": 1234,\n" +
                                "  \"amount\": 1000\n" +
                                "}"))
                .andExpect(status().isPaymentRequired());
    }

    @Test
    public void failed_withdraw_wrong_pin_code() throws Exception {
        mockMvc.perform(post("/transaction/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"number\": 1000000,\n" +
                                "  \"pinCode\": 1111,\n" +
                                "  \"amount\": 1000\n" +
                                "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void success_transfer() throws Exception {
        mockMvc.perform(post("/transaction/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"senderNumber\": 1000001,\n" +
                                "  \"pinCode\": 1111,\n" +
                                "  \"receiverNumber\": 1000000,\n" +
                                "  \"amount\": 500\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("number", is(1000001)));
    }
}
