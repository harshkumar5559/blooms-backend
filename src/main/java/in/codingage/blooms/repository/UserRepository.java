package in.codingage.blooms.repository;

import in.codingage.blooms.models.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    // Custom method : Username se dhundhane ke liye
    // Spring boot naam padh ke samajh jayega ki query kya banani hai!
    Optional<User> findByUsername(String username);

    // Email Se dhundhane ke Liye
    Optional<User> findByEmail(String email);

}
