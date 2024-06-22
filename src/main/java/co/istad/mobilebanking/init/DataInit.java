package co.istad.mobilebanking.init;

import co.istad.mobilebanking.domain.Role;
import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.domain.account.AccountType;
import co.istad.mobilebanking.features.accounttype.AccountTypeRepository;
import co.istad.mobilebanking.features.user.RoleRepository;
import co.istad.mobilebanking.features.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final UserRepository userRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
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
            Role user = new Role();
            user.setName("USER");

            Role customer = new Role();
            customer.setName("CUSTOMER");

            Role manager = new Role();
            manager.setName("MANAGER");

            Role admin = new Role();
            admin.setName("ADMIN");

            roleRepository.saveAll(List.of(user, customer, manager, admin));
            User user1 = new User();
            user1.setUuid(UUID.randomUUID().toString());
            user1.setName("Lyzhia");
            user1.setGender("Female");
            user1.setPhoneNumber("092212727");
            user1.setEmail("lyzhia@istad.co");
            user1.setPin("123");
            user1.setPassword(passwordEncoder.encode("123456"));
            user1.setNationalCardId("123456789");
            user1.setProfileImage("user.avatar");
            user1.setIsBlocked(false);
            user1.setIsDeleted(false);
            user1.setIsVerified(true);
            user1.setRoles(List.of(user, admin));

            User user2 = new User();
            user2.setUuid(UUID.randomUUID().toString());
            user2.setName("Lyza");
            user2.setGender("Female");
            user2.setPhoneNumber("010682229");
            user2.setEmail("lyza@istad.co");
            user2.setPin("456");
            user2.setPassword(passwordEncoder.encode("678919"));
            user2.setNationalCardId("112233445");
            user2.setProfileImage("user.avatar");
            user2.setIsBlocked(false);
            user2.setIsDeleted(false);
            user2.setIsVerified(true);
            user2.setRoles(List.of(user, manager));

            User user3 = new User();
            user3.setUuid(UUID.randomUUID().toString());
            user3.setName("Lyzi");
            user3.setGender("Female");
            user3.setPhoneNumber("010682225");
            user3.setEmail("lyzhi@istad.co");
            user3.setPin("344");
            user3.setPassword(passwordEncoder.encode("112233"));
            user3.setNationalCardId("666777888");
            user3.setProfileImage("user.avatar");
            user3.setIsBlocked(false);
            user3.setIsDeleted(false);
            user3.setIsVerified(true);
            user3.setRoles(List.of(user, customer));

//        userRepository.save(user1);
//        userRepository.save(user2);
            userRepository.saveAll(List.of(user1, user2, user3));

        }


    }
}
