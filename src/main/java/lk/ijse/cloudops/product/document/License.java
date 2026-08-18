package lk.ijse.cloudops.product.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "licenses")
public class License {

    @Id
    private String id;

    @Indexed(unique = true)
    private String licenseKey; // LIC-YYYY-XXXX-XXXX-X

    @Indexed
    private String clientId;

    @Indexed
    private String productId;

    private String planTier; // BASIC, PRO, ENTERPRISE
    private int maxSeats;
    private int assignedSeats;

    private LocalDate validFrom;
    private LocalDate validUntil;

    @Builder.Default
    private boolean isActive = true;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
