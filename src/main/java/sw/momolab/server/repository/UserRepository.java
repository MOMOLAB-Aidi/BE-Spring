package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.momolab.server.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
}