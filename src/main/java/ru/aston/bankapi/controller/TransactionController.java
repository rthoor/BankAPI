package ru.aston.bankapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.bankapi.dto.*;
import ru.aston.bankapi.service.AccountService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final AccountService accountService;

    @PostMapping(value = "/deposit")
    @Operation(summary = "Deposit Money On Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Account Not Found Or Is Inactive", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
    })
    public ResponseEntity<AccountDto> deposit(@Valid @RequestBody DepositDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(AccountDto.from(accountService.deposit(dto)));
    }

    @PostMapping(value = "/withdraw")
    @Operation(summary = "Withdraw money from account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Account Not Found Or Is Inactive", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "402", description = "Not Enough Balance", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Wrong PIN-Code", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
    })
    public ResponseEntity<AccountDto> withdraw(@Valid @RequestBody WithdrawDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(AccountDto.from(accountService.withdraw(dto)));
    }

    @PostMapping(value = "/transfer")
    @Operation(summary = "Transfer money to another account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Account Not Found Or Is Inactive", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "402", description = "Not Enough Balance", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Wrong PIN-Code", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
    })
    public ResponseEntity<AccountDto> transfer(@Valid @RequestBody TransferDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(AccountDto.from(accountService.transfer(dto)));
    }
}
