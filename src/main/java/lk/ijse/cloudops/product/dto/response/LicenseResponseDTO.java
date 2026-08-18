package lk.ijse.cloudops.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenseResponseDTO {
    private String id;
    private String licenseKey;
    private String clientId;
    private String productId;
    private String planTier;
    private int maxSeats;
    private int assignedSeats;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private boolean isActive;
    private Instant createdAt;
}
