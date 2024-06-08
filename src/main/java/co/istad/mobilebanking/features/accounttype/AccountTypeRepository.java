package co.istad.mobilebanking.features.accounttype;

import co.istad.mobilebanking.domain.account.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
    // SELECT * FROM account_types WHERE alias = ?
    Optional<AccountType> findByAlias(String alias);
    //SELECT * EXISTS(SELECT * FROM)
    boolean existsByName(String name);
    boolean existsByAlias(String alias);
    //Select * from account_types WHERE id = ?
    Optional<AccountType> findById(Integer id);
}
