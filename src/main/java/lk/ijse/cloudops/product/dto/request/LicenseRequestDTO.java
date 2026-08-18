package lk.ijse.cloudops.product.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenseRequestDTO {

    @NotBlank(message = "Client ID is required")
    private String clientId;

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "Plan tier is required")
    private String planTier;

    @Min(value = 1, message = "Max seats must be at least 1")
    private int maxSeats;

    @NotNull(message = "Valid from date is required")
    private LocalDate validFrom;

    @NotNull(message = "Valid until date is required")
    private LocalDate validUntil;
}
