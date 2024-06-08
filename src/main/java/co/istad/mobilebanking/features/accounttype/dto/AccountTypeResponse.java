package co.istad.mobilebanking.features.accounttype.dto;

public record AccountTypeResponse(
        String alias,
        String name,
        String description,
        Boolean isDeleted
) {
}
