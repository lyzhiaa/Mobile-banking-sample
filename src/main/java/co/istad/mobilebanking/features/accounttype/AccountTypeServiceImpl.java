package co.istad.mobilebanking.features.accounttype;

import co.istad.mobilebanking.domain.account.AccountType;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeCreateRequest;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeResponse;
import co.istad.mobilebanking.features.accounttype.dto.AccountTypeUpdateRequest;
import co.istad.mobilebanking.mapper.AccountTypeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j //use for called data
public class AccountTypeServiceImpl implements AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;
    @Override
    public AccountTypeResponse createNew(AccountTypeCreateRequest accountTypeCreateRequest) {
        //validate account type
        if (accountTypeRepository.existsByAlias(accountTypeCreateRequest.alias())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This kind of alias is already exist!!!");
        }
        //validate accountType name
//        if (accountTypeRepository.existsByName(accountTypeCreateRequest.name())) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT,
//                    "You are already create this kind(name) of account!!!");
//        };
        //Transfer DTO to Domain model
        AccountType accountType = accountTypeMapper.fromAccountTypeCreateRequest(accountTypeCreateRequest);
        accountType.setIsDeleted(false);
        accountType = accountTypeRepository.save(accountType);

        //System generate data
        accountType.setAlias(accountType.getAlias());
        accountType.setName(accountType.getName());
        accountType.setDescription(accountType.getDescription());

        //save accountType to database
        accountType = accountTypeRepository.save(accountType);

        return accountTypeMapper.toAccountTypeResponse(accountType);

    }

    @Override
    public List<AccountTypeResponse> findAccountType() {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        List<AccountType> accountTypes = accountTypeRepository.findAll(sortById);

        return accountTypeMapper.toAccountTypeResponseList(accountTypes);

    }

    @Override
    public AccountTypeResponse findSingleAccount(String alias) {
        AccountType accountType = accountTypeRepository.findByAlias(alias)
                .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This accountType not found!"));
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }

    //find account by id
//    @Override
//    public AccountTypeResponse findById(Integer id) {
//        AccountType accountType = accountTypeRepository.findById(id)
//                .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND,
//                        "This accountType not found!"));
//        return accountTypeMapper.toAccountTypeResponse(accountType);
//    }

    //delete account by id
    @Override
    public void deleteAccountType(Integer id) {
        AccountType accountType = accountTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Can not delete because This accountType not found!"));
        accountTypeRepository.delete(accountType);


    }
    //Update
    @Override
    public AccountTypeResponse updateAccountType(String alias, AccountTypeUpdateRequest accountTypeUpdateRequest) {
        AccountType accountType = accountTypeRepository.findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This account-type not found!"));
//        if (accountTypeUpdateRequest.description() != null)
//            accountType.setDescription(accountTypeUpdateRequest.description());

        log.info("Before map : {}, {}, {}", accountType.getId(), accountType.getDescription(), accountType.getIsDeleted());
        accountTypeMapper.fromAccountTypeUpdateRequest(accountTypeUpdateRequest, accountType);
        log.info("After map : {}, {}, {}", accountType.getId(), accountType.getDescription(), accountType.getIsDeleted());

        accountType = accountTypeRepository.save(accountType);
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }
}
