package ru.aston.bankapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.bankapi.dto.AccountDto;
import ru.aston.bankapi.dto.CreateAccountDto;
import ru.aston.bankapi.dto.TransactionHistoryDto;
import ru.aston.bankapi.service.AccountService;
import ru.aston.bankapi.service.TransactionHistoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final TransactionHistoryService transactionHistoryService;

    @PostMapping(value = "/create")
    @Operation(summary = "Create An Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))
            })
    })
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AccountDto.from(accountService.createAccount(dto)));
    }

    @GetMapping(value = "/{number}")
    @Operation(summary = "Get An Account By Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))
            })
    })
    public ResponseEntity<AccountDto> getAccount(@Min(1000000) @Max(9999999) @PathVariable("number") int number) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(AccountDto.from(accountService.getAccountByNumber(number)));
    }

    @GetMapping(value = "/all")
    @Operation(summary = "Get All Accounts With History")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))
            })
    })
    public ResponseEntity<List<AccountDto>> getAllAccountsWithHistory(@Parameter(description = "Beneficiary Name") @RequestParam(required = false) String beneficiaryName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(AccountDto.from(accountService.getAllAccountsWithHistory(beneficiaryName)));
    }

    @GetMapping(value = "/{number}/getHistory")
    @Operation(summary = "Get All Transaction History By Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionHistoryDto.class))
            })
    })
    public ResponseEntity<List<TransactionHistoryDto>> getHistory(@Min(1000000) @Max(9999999) @PathVariable("number") int number) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(TransactionHistoryDto.from(transactionHistoryService.getHistoryByAccountNumber(number)));
    }

}
