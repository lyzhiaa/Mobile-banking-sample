package co.istad.mobilebanking.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ErrorResponse<T> {
    private Integer code;
    private T reason;
}
