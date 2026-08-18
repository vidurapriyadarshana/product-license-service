package lk.ijse.cloudops.product.dto.response;

import lk.ijse.cloudops.product.document.PricingTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private String id;
    private String productId;
    private String name;
    private String category;
    private String description;
    private String version;
    private List<String> features;
    private List<PricingTier> pricingTiers;
    private String gcsManualUrl;
    private String gcsInstallerUrl;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
