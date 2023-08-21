package ru.aston.bankapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "TransferRequest")
public class TransferDto {
    @NotNull
    @Min(1000000)
    @Max(9999999)
    @Schema(description = "Sender-Account Number", example = "1000000")
    private Integer senderNumber;

    @NotNull
    @Min(1000000)
    @Max(9999999)
    @Schema(description = "Receiver-Account Number", example = "1000001")
    private Integer receiverNumber;

    @NotNull
    @Min(1000)
    @Max(9999)
    @Schema(description = "PIN-Code", example = "1234")
    private Integer pinCode;

    @NotNull
    @Min(1)
    @Schema(description = "Transferred Amount", example = "199")
    private Integer amount;
}
