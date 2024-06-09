package co.istad.mobilebanking.features.accounttype;

import co.istad.mobilebanking.domain.account.AccountType;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeCreateRequest;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeResponse;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-types")
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    //Create account-types
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    AccountTypeResponse createNew(@Valid @RequestBody AccountTypeCreateRequest accountTypeCreateRequest) {
        return accountTypeService.createNew(accountTypeCreateRequest);
    }

    //Find all account-types
    @GetMapping
    List<AccountTypeResponse> findAccountType() {
        return accountTypeService.findAccountType();
    }

    //Find account-types by id
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{alias}")
    AccountTypeResponse findSingleAccountType(@Valid @PathVariable String alias) {
        return accountTypeService.findSingleAccount(alias);
    }

    //Delete by id
    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/{id}")
    void deleteById(@Valid @PathVariable Integer id) {
        accountTypeService.deleteAccountType(id);
    }

    //Update
    @PatchMapping("/{alias}")
    AccountTypeResponse updateByAlias(@Valid @PathVariable String alias, @RequestBody AccountTypeUpdateRequest accountTypeUpdateRequest) {
        return accountTypeService.updateAccountType(alias, accountTypeUpdateRequest);
    }
}
