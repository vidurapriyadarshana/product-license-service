package lk.ijse.cloudops.product.controller;

import jakarta.validation.Valid;
import lk.ijse.cloudops.product.dto.request.LicenseRequestDTO;
import lk.ijse.cloudops.product.dto.request.LicenseUpdateDTO;
import lk.ijse.cloudops.product.dto.response.LicenseListDTO;
import lk.ijse.cloudops.product.dto.response.LicenseResponseDTO;
import lk.ijse.cloudops.product.dto.response.StandardResponse;
import lk.ijse.cloudops.product.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @PostMapping
    public ResponseEntity<StandardResponse<LicenseResponseDTO>> issueLicense(
            @Valid @RequestBody LicenseRequestDTO requestDTO) {
        LicenseResponseDTO issued = licenseService.issueLicense(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StandardResponse.success(HttpStatus.CREATED.value(), "License issued successfully", issued));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<LicenseResponseDTO>> getLicenseById(@PathVariable String id) {
        LicenseResponseDTO license = licenseService.getLicenseById(id);
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "License retrieved successfully", license));
    }

    @GetMapping("/key/{licenseKey}")
    public ResponseEntity<StandardResponse<LicenseResponseDTO>> getLicenseByKey(@PathVariable String licenseKey) {
        LicenseResponseDTO license = licenseService.getLicenseByKey(licenseKey);
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "License retrieved successfully", license));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<StandardResponse<List<LicenseListDTO>>> getLicensesByClient(@PathVariable String clientId) {
        List<LicenseListDTO> licenses = licenseService.getLicensesByClient(clientId);
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "Licenses retrieved successfully", licenses));
    }

    @GetMapping
    public ResponseEntity<StandardResponse<List<LicenseListDTO>>> getAllLicenses() {
        List<LicenseListDTO> licenses = licenseService.getAllLicenses();
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "Licenses retrieved successfully", licenses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse<LicenseResponseDTO>> updateLicense(
            @PathVariable String id,
            @RequestBody LicenseUpdateDTO updateDTO) {
        LicenseResponseDTO updated = licenseService.updateLicense(id, updateDTO);
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "License updated successfully", updated));
    }

    @PatchMapping("/{id}/revoke")
    public ResponseEntity<StandardResponse<Void>> revokeLicense(@PathVariable String id) {
        licenseService.revokeLicense(id);
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "License revoked successfully", null));
    }
}
