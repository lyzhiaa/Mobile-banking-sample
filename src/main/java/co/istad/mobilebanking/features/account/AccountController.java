package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.features.account.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    //create account
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    AccountResponse createNew(@Valid @RequestBody AccountCreateRequest accountCreateRequest) {
        return accountService.createNew(accountCreateRequest);
    }

    //Pagination
    @GetMapping
    Page<AccountResponse> findList(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize
    ) {
        return accountService.findList(pageNumber, pageSize);
    }
    //find account by actNo
    @GetMapping("/{actNo}")
    AccountResponse findAccountByActNo(@Valid @PathVariable("actNo") String actNo) {
        return accountService.findByActNo(actNo);
    }

    // rename account
    @PutMapping("/{actNo}/rename")
    AccountResponse renameAccount(@PathVariable("actNo") String actNo,
                                  @Valid @RequestBody AccountRenameRequest accountRenameRequest){
        return accountService.renameAccount(actNo, accountRenameRequest);
    }

    //hide account
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{actNo}/hide-account")
    void hideAccount(@PathVariable("actNo") String actNo){
        accountService.hideAccount(actNo);
    }

    //Limit transfer
    @PutMapping("/{actNo}/transfer-limit")
    void transferLimit(@PathVariable("actNo") String actNo,
                       @Valid @RequestBody UpdateTransferLimitRequest accountTransferLimitRequest){
        accountService.updateTransferLimit(actNo, accountTransferLimitRequest);
    }
    //=======================================[HomeWork]============================================
    // soft Delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{actNo}/soft-delete")
    void softDelete(@PathVariable("actNo") String actNo) {
        accountService.softDelete(actNo);
    }

    // Hard delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{actNo}/hard-delete")
    void hardDelete(@Valid @PathVariable("actNo") String actNo) {
        accountService.hardDelete(actNo);
    }

    //update
    @PatchMapping("/{actNo}/update-by-act-no")
    AccountResponse updateAccountByActNo(@Valid @PathVariable("actNo") String actNo,
                                  @RequestBody AccountUpdateRequest accountUpdateRequest) {
        return accountService.updateAccount(actNo, accountUpdateRequest);
    }




}
