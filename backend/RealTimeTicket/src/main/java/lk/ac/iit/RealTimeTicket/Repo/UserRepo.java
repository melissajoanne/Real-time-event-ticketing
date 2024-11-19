package lk.ac.iit.RealTimeTicket.Repo;

import lk.ac.iit.RealTimeTicket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
}
