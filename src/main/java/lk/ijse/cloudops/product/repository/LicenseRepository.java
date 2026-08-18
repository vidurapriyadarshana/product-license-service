package lk.ijse.cloudops.product.repository;

import lk.ijse.cloudops.product.document.License;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseRepository extends MongoRepository<License, String> {
    Optional<License> findByLicenseKey(String licenseKey);
    List<License> findByClientId(String clientId);
    List<License> findByProductId(String productId);
}
