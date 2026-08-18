package lk.ijse.cloudops.product.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class LicenseKeyFactory {

    public String generateLicenseKey() {
        String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String rawUuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String part1 = rawUuid.substring(0, 4);
        String part2 = rawUuid.substring(4, 8);
        String part3 = rawUuid.substring(8, 9);
        return String.format("LIC-%s-%s-%s-%s", year, part1, part2, part3);
    }
}
