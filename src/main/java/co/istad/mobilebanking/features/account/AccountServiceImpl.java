package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.domain.user.User;
import co.istad.mobilebanking.domain.account.Account;
import co.istad.mobilebanking.domain.account.AccountType;
import co.istad.mobilebanking.features.account.dto.*;
import co.istad.mobilebanking.features.accounttype.AccountTypeRepository;
import co.istad.mobilebanking.features.user.UserRepository;
import co.istad.mobilebanking.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponse createNew(AccountCreateRequest accountCreateRequest) {
        //validate account type
        AccountType accountType = accountTypeRepository.findByAlias(accountCreateRequest.accountTypeAlias()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This accountType is not has been found"));
        //validate User
        User user = userRepository.findByUuid((accountCreateRequest.userUuid())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has not been found"));

        //validate account number (account no)
        if (accountRepository.existsByActNo(accountCreateRequest.actNo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "THis account number has already exist!!!");
        }

        //validate balance
        if (accountCreateRequest.balance().compareTo(BigDecimal.valueOf(10)) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance 10$ is Required to create account");

        }

        //Transfer DTO to domain model
        Account account = accountMapper.fromAccountCreateRequest(accountCreateRequest);
        account.setAccountType(accountType);
        account.setUser(user);

        //System generate data
        account.setActName(user.getName());
        account.setIsHidden(false);
        account.setTransferLimit(BigDecimal.valueOf(1000));

        //save account into database and get latest date back
        account = accountRepository.save(account);

        //Transfer Domain Model to DTO
//        /**
//         return AccountResponse.builder()
//         .alias(account.getAlias())
//         .actName(account.getActName())
//         .accountType(account.getAccountType().getName())
//         .actNo(account.getActNo())
//         .balance(account.getBalance())
//         .build();
//         *
//         */

        return accountMapper.toAccountResponse(account);
    }

    @Override
    public List<AccountResponse> findList() {
        return List.of();
    }

    @Override
    public AccountResponse findByActNo(String actNo) {
        //Validate by account number
        Account account = accountRepository.findByActNo(actNo).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This account has not been found!"));
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public Page<AccountResponse> findList(int pageNumber, int pageSize) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortById);

        Page<Account> accounts = accountRepository.findAll(pageRequest);

        return accounts.map(accountMapper::toAccountResponse);
    }

    //========================Rename Account =========================
    @Override
    public AccountResponse renameAccount(String actNo, AccountRenameRequest accountRenameRequest) {
        //Validate by account number
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This account has not been found!"));
        account.setAlias(accountRenameRequest.alias());
        account = accountRepository.save(account);

        return accountMapper.toAccountResponse(account);
    }


    @Override
    public void hideAccount(String actNo) {
        //Validate by account number
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This account has not been found!"));
        account.setIsHidden(true);
        accountRepository.save(account);

    }

    @Override
    public void updateTransferLimit(String actNo, UpdateTransferLimitRequest accountTransferLimitRequest) {
        //Validate by account number
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This account has not been found!"));
        account.setTransferLimit(accountTransferLimitRequest.amount());
        accountRepository.save(account);
    }
//=======================================[HomeWork]============================================
    @Override
    public void softDelete(String actNo) {
        //Validate by account number
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This account has not been found!"));
        account.setIsDeleted(true);
        accountRepository.save(account);
    }

    @Override
    public void hardDelete(String actNo) {
        //Validate by account number
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This account has not been found!"));
        accountRepository.delete(account);
    }

    @Override
    public AccountResponse updateAccount(String actNo, AccountUpdateRequest accountUpdateRequest) {
        //Validate by account number
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This account has not been found!"));
        accountMapper.fromAccountUpdateRequest(accountUpdateRequest, account);
        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);
    }


}
