package lk.ijse.cloudops.product.repository;

import lk.ijse.cloudops.product.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByProductId(String productId);
    List<Product> findByStatus(String status);
    List<Product> findByCategory(String category);
}
