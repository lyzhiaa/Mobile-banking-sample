package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //select * from where uuid = ?
    Optional<User> findByUuid(String uuid);

    Optional<User> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByPassword(String password);
    Boolean existsByNationalCardId(String nationalCardId);

    Optional<User> findByEmail(String email);
}
