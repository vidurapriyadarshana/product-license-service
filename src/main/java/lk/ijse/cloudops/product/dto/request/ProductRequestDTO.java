package lk.ijse.cloudops.product.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class ProductRequestDTO {

    @NotBlank(message = "Product ID is required (e.g. PROD-ERP-01)")
    private String productId;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    private String description;
    private String version;
    private List<String> features;
    private List<PricingTier> pricingTiers;
}
