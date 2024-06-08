package co.istad.mobilebanking.features.account.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record AccountUpdateRequest(
        @NotBlank(message = "alias is Require !!!")
        String alias,
        @NotBlank(message = "actName is Require !!!")
        String actName,
        @Min(1000)
        BigDecimal transferLimit,
        Boolean isHidden
) {
}
