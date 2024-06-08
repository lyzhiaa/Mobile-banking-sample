package co.istad.mobilebanking.features.accounttype.dto;

public record AccountTypeUpdateRequest(
        String description,
        Boolean isDeleted
) {
}
