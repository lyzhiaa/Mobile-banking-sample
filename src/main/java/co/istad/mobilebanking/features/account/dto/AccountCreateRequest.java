package co.istad.mobilebanking.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

public record AccountCreateRequest(
        @NotBlank(message = "Account number is required!!!")
        String actNo,
        @NotNull(message = "Balance is Required!!!")
                @Positive
        BigDecimal balance,
        @NotBlank(message = "AccountType is Required!!!")
        String accountTypeAlias,
        //User
        @NotBlank(message = "User uuid is Required!!!")
        String userUuid
) {
}
