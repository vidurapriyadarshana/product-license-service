package lk.ijse.cloudops.product.service;

import lk.ijse.cloudops.product.document.License;
import lk.ijse.cloudops.product.dto.request.LicenseRequestDTO;
import lk.ijse.cloudops.product.dto.request.LicenseUpdateDTO;
import lk.ijse.cloudops.product.dto.response.LicenseListDTO;
import lk.ijse.cloudops.product.dto.response.LicenseResponseDTO;
import lk.ijse.cloudops.product.exception.NotFoundException;
import lk.ijse.cloudops.product.repository.LicenseRepository;
import lk.ijse.cloudops.product.repository.ProductRepository;
import lk.ijse.cloudops.product.util.LicenseKeyFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final ProductRepository productRepository;
    private final LicenseKeyFactory licenseKeyFactory;
    private final ModelMapper modelMapper;

    public LicenseResponseDTO issueLicense(LicenseRequestDTO requestDTO) {
        // Validate product existence if needed
        productRepository.findByProductId(requestDTO.getProductId());

        String licenseKey = licenseKeyFactory.generateLicenseKey();

        License license = License.builder()
                .licenseKey(licenseKey)
                .clientId(requestDTO.getClientId())
                .productId(requestDTO.getProductId())
                .planTier(requestDTO.getPlanTier())
                .maxSeats(requestDTO.getMaxSeats())
                .assignedSeats(0)
                .validFrom(requestDTO.getValidFrom())
                .validUntil(requestDTO.getValidUntil())
                .isActive(true)
                .createdAt(Instant.now())
                .build();

        License saved = licenseRepository.save(license);
        return modelMapper.map(saved, LicenseResponseDTO.class);
    }

    public LicenseResponseDTO getLicenseById(String id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("License not found with id: " + id));
        return modelMapper.map(license, LicenseResponseDTO.class);
    }

    public LicenseResponseDTO getLicenseByKey(String licenseKey) {
        License license = licenseRepository.findByLicenseKey(licenseKey)
                .orElseThrow(() -> new NotFoundException("License not found with key: " + licenseKey));
        return modelMapper.map(license, LicenseResponseDTO.class);
    }

    public List<LicenseListDTO> getLicensesByClient(String clientId) {
        return licenseRepository.findByClientId(clientId).stream()
                .map(l -> modelMapper.map(l, LicenseListDTO.class))
                .collect(Collectors.toList());
    }

    public List<LicenseListDTO> getAllLicenses() {
        return licenseRepository.findAll().stream()
                .map(l -> modelMapper.map(l, LicenseListDTO.class))
                .collect(Collectors.toList());
    }

    public LicenseResponseDTO updateLicense(String id, LicenseUpdateDTO updateDTO) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("License not found with id: " + id));

        if (updateDTO.getMaxSeats() != null) license.setMaxSeats(updateDTO.getMaxSeats());
        if (updateDTO.getAssignedSeats() != null) license.setAssignedSeats(updateDTO.getAssignedSeats());
        if (updateDTO.getValidUntil() != null) license.setValidUntil(updateDTO.getValidUntil());
        if (updateDTO.getIsActive() != null) license.setActive(updateDTO.getIsActive());

        License updated = licenseRepository.save(license);
        return modelMapper.map(updated, LicenseResponseDTO.class);
    }

    public void revokeLicense(String id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("License not found with id: " + id));
        license.setActive(false);
        licenseRepository.save(license);
    }
}
