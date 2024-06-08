package co.istad.mobilebanking.features.accounttype.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AccountTypeCreateRequest(
        @NotBlank(message = "Alias is require!")
        String alias,
        @NotBlank(message = "name is require!")
        String name,
        @NotBlank(message = "name is require!")
        String description,
        Boolean isDeleted
) {
}
