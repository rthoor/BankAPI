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
@Schema(description = "WithdrawRequest")
public class WithdrawDto {
    @NotNull
    @Min(1000000)
    @Max(9999999)
    @Schema(description = "Account Number", example = "1000000")
    private Integer number;

    @NotNull
    @Min(1000)
    @Max(9999)
    @Schema(description = "PIN-Code", example = "1234")
    private Integer pinCode;

    @NotNull
    @Min(1)
    @Schema(description = "Withdrew Amount", example = "199")
    private Integer amount;
}
