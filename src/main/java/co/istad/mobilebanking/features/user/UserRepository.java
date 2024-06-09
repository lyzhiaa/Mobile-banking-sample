package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //select * from where uuid = ?
    Optional<User> findByUuid(String uuid);
}
