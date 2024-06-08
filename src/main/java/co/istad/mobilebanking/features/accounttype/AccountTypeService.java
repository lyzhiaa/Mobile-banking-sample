package co.istad.mobilebanking.features.accounttype;

import co.istad.mobilebanking.domain.account.AccountType;
import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeCreateRequest;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeResponse;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeUpdateRequest;

import java.util.List;

public interface AccountTypeService {
    /**
     * create a new account type
     * @param accountCreateRequest {@link AccountCreateRequest}
     * @return {@link AccountTypeResponse}
     */
     AccountTypeResponse createNew(AccountTypeCreateRequest accountCreateRequest);

    /**
     * find all account type
     *
     * @return {@link List<AccountTypeResponse>}
     */
    List<AccountType> findAccountType();

    /**
     *
     * @param alias
     * @return {@link AccountTypeResponse}
     */
//    AccountTypeResponse findById(Integer id);
//    AccountTypeResponse findByAlias(String alias);

    //find account by id
//    @Override
//    public AccountTypeResponse findById(Integer id) {
//        AccountType accountType = accountTypeRepository.findById(id)
//                .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND,
//                        "This accountType not found!"));
//        return accountTypeMapper.toAccountTypeResponse(accountType);
//    }
    AccountTypeResponse findSingleAccount(String alias);

    /**
     * delete account-types by id
     * @param id
     */
    void deleteAccountType(Integer id);

    /**
     * update account-type
     *
     * @param alias
     * @return {@link AccountTypeResponse}
     */
    AccountTypeResponse updateAccountType(String alias, AccountTypeUpdateRequest accountTypeUpdateRequest);


}
