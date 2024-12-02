package lk.ac.iit.RealTimeEventTicketing.repo;

import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepo extends JpaRepository<Vendor,Long> {
    Optional<Vendor> findById(Long vendor);
}
