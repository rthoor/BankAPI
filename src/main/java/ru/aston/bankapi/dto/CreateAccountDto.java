package ru.aston.bankapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "CreateAccountRequest")
public class CreateAccountDto {
    @NotBlank
    @Schema(description = "Beneficiary Name", example = "Name Surname")
    private String beneficiaryName;

    @NotNull
    @Min(1000)
    @Max(9999)
    @Schema(description = "PIN-Code", example = "7777")
    private Integer pinCode;
}
