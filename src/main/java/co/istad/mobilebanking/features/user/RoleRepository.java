package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.domain.Role;
import co.istad.mobilebanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // JPQL = Jakarta Persistent Query Language
    @Query("""
        SELECT r 
        FROM Role As r
        WHERE r.name = 'USER'       
        """)
    Role findRoleUser();

    @Query("SELECT r FROM Role AS r WHERE r.name = 'CUSTOMER'")
            Role findRoleCustomer();
}
