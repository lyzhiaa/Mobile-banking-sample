package co.istad.mobilebanking.features.account.dto;

import co.istad.mobilebanking.features.accounttype.dto.AccountTypeResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountResponse(
        String alias,
        String actName,
        String actNo,
        BigDecimal balance,
        Boolean isDeleted,
//        String accountTypeAlias
        Boolean isHidden,
        AccountTypeResponse accountType
) {

}
