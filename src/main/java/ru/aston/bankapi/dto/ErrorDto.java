package ru.aston.bankapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "ErrorDto")
public class ErrorDto {
    @Schema(description = "Error Message", example = "Something went wrong")
    private String message;
}
