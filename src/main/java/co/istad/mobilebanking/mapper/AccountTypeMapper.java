package co.istad.mobilebanking.mapper;

import co.istad.mobilebanking.domain.account.AccountType;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeCreateRequest;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeResponse;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface AccountTypeMapper {
    AccountTypeResponse toAccountTypeResponse(AccountType accountType);
    //Partially update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromAccountTypeUpdateRequest(AccountTypeUpdateRequest accountTypeUpdateRequest,@MappingTarget AccountType accountType );
    AccountType fromAccountTypeCreateRequest(AccountTypeCreateRequest accountCreateRequest);
    List<AccountType> toAccountTypeResponseList(List<AccountType> accountTypes);

}
