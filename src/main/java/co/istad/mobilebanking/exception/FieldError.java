package co.istad.mobilebanking.exception;

import lombok.Builder;

@Builder
public record FieldError(
        String field,
        String detail
) {
}
