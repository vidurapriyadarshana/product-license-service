package lk.ijse.cloudops.product.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingTier {
    private String tier; // BASIC, PRO, ENTERPRISE
    private BigDecimal monthlyPrice;
    private int maxUsers;
}
