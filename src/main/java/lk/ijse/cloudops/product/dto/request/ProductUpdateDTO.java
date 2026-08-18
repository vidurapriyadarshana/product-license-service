package lk.ijse.cloudops.product.dto.request;

import lk.ijse.cloudops.product.document.PricingTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateDTO {
    private String name;
    private String category;
    private String description;
    private String version;
    private String status; // ACTIVE, DEPRECATED, ARCHIVED
    private List<String> features;
    private List<PricingTier> pricingTiers;
}
