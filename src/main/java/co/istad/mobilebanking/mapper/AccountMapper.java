package co.istad.mobilebanking.mapper;

import co.istad.mobilebanking.domain.account.Account;
import co.istad.mobilebanking.domain.account.AccountType;
import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.account.dto.AccountResponse;
import co.istad.mobilebanking.features.account.dto.AccountUpdateRequest;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    //Map account to AccountResponse
    //source = Account
    //Target (ReturnType) = AccountResponse
//    @Mapping(source = "accountType.alias", target = "accountTypeAlias")
    AccountResponse toAccountResponse(Account account);
    Account fromAccountCreateRequest(AccountCreateRequest accountCreateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromAccountUpdateRequest(AccountUpdateRequest accountUpdateRequest, @MappingTarget Account account);
    }
