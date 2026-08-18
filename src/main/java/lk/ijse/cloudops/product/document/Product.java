package lk.ijse.cloudops.product.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @Indexed(unique = true)
    private String productId; // PROD-ERP-01 (unique business key)

    private String name;
    private String category; // SaaS Platform, API Service, Desktop App
    private String description;
    private String version;

    @Builder.Default
    private List<String> features = new ArrayList<>();

    @Builder.Default
    private List<PricingTier> pricingTiers = new ArrayList<>();

    private String gcsManualUrl;
    private String gcsInstallerUrl;

    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, DEPRECATED, ARCHIVED

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();
}
