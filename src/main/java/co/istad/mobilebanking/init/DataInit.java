package co.istad.mobilebanking.init;

import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.domain.account.AccountType;
import co.istad.mobilebanking.features.accounttype.AccountTypeRepository;
import co.istad.mobilebanking.features.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final UserRepository userRepository;
    private final AccountTypeRepository accountTypeRepository;
    @PostConstruct
    void init(){
        //accountType repository
        if (accountTypeRepository.count() == 0) {
            AccountType payroll = new AccountType();
            payroll.setName("payroll");
            payroll.setAlias("Payroll");
            payroll.setIsDeleted(false);
            payroll.setDescription("Payroll Account User");

            AccountType saving = new AccountType();
            saving.setName("saving");
            saving.setAlias("Saving");
            saving.setIsDeleted(false);
            saving.setDescription("saving Account User");

            accountTypeRepository.saveAll(List.of(payroll, saving));
        }
        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setUuid(UUID.randomUUID().toString());
            user1.setName("Lyzhia");
            user1.setGender("Female");
            user1.setPhoneNumber("092212727");
            user1.setPin("123");
            user1.setPassword("123456");
            user1.setNationalCardId("123456789");
            user1.setProfileImage("user.avatar");
            user1.setIsBlocked(false);
            user1.setIsDeleted(false);

            User user2 = new User();
            user2.setUuid(UUID.randomUUID().toString());
            user2.setName("Lyza");
            user2.setGender("Female");
            user2.setPhoneNumber("010682229");
            user2.setPin("456");
            user2.setPassword("678919");
            user2.setNationalCardId("112233445");
            user2.setProfileImage("user.avatar");
            user2.setIsBlocked(false);
            user2.setIsDeleted(false);

//        userRepository.save(user1);
//        userRepository.save(user2);
            userRepository.saveAll(List.of(user1, user2));
        }


    }
}
