package lk.ijse.cloudops.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenseUpdateDTO {
    private Integer maxSeats;
    private Integer assignedSeats;
    private LocalDate validUntil;
    private Boolean isActive;
}
