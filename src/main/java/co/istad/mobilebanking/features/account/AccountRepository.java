package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.domain.account.Account;
import co.istad.mobilebanking.domain.account.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    //SELECT * EXISTS(SELECT * FROM)
    boolean existsByActNo(String actNo);
    Optional<Account> findByActNo(String actNo);
}
