package co.istad.mobilebanking.features.account.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateTransferLimitRequest(
        @NotNull(message = "amount is require!!!")
                @Min(1000)
        BigDecimal amount
) {
}
