package co.istad.mobilebanking.features.account.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountRenameRequest(
        @NotBlank(message = "alias is required")
        String alias
) {
}
