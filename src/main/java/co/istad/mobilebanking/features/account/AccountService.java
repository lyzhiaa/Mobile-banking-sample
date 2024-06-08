package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.features.account.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    /**
     * create a new account
     * @param accountCreateRequest
     * @return {@link AccountResponse}
     */
    AccountResponse createNew(AccountCreateRequest accountCreateRequest);

    /**
     * find all accounts
     * @return {@link List<AccountResponse>}
     */
    List<AccountResponse> findList();

    /**
     * find account by id
     * @param actNo is no if no account
     * @return {@link AccountResponse}
     */
    AccountResponse findByActNo(String actNo);

    /**
     * find account by account no
     * @param pageNumber is current page request form cilent
     * @param pageSize is size of record per page from client
     * @return {@link List<AccountResponse>}
     */
    Page<AccountResponse> findList(int pageNumber, int pageSize);

    /**
     *
     * @param actNo
     * @param accountRenameRequest
     * @return {@link AccountResponse}
     */
    AccountResponse renameAccount(String actNo, AccountRenameRequest accountRenameRequest);

    /**
     * hire account
     */
    void hideAccount(String actNo);

    /**
     *
     * @param actNo
     * @param accountTransferLimitRequest
     */
    void updateTransferLimit(String actNo, UpdateTransferLimitRequest accountTransferLimitRequest);
    //=======================================[HomeWork]============================================

    /**
     * soft delete
     * @param actNo
     */
    void softDelete(String actNo);

    /**
     * hard delete
     * @param actNo
     */
    void hardDelete(String actNo);

    /**
     *
     * @param actNo
     * @param accountUpdateRequest
     * @return {@link AccountResponse}
     */
    AccountResponse updateAccount(String actNo, AccountUpdateRequest accountUpdateRequest);
}
