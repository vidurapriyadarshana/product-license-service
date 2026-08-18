package lk.ijse.cloudops.product.service;

import lk.ijse.cloudops.product.document.Product;
import lk.ijse.cloudops.product.dto.request.ProductRequestDTO;
import lk.ijse.cloudops.product.dto.request.ProductUpdateDTO;
import lk.ijse.cloudops.product.dto.response.ProductResponseDTO;
import lk.ijse.cloudops.product.dto.response.ProductSummaryDTO;
import lk.ijse.cloudops.product.exception.BadRequestException;
import lk.ijse.cloudops.product.exception.NotFoundException;
import lk.ijse.cloudops.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final GcsStorageService gcsStorageService;
    private final ModelMapper modelMapper;

    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO, MultipartFile manualFile, MultipartFile installerFile) {
        if (productRepository.findByProductId(requestDTO.getProductId()).isPresent()) {
            throw new BadRequestException("Product already exists with productId: " + requestDTO.getProductId());
        }

        String manualUrl = null;
        if (manualFile != null && !manualFile.isEmpty()) {
            String objectName = String.format("manuals/%s_%s", UUID.randomUUID(), manualFile.getOriginalFilename());
            manualUrl = gcsStorageService.uploadAsset(objectName, manualFile);
        }

        String installerUrl = null;
        if (installerFile != null && !installerFile.isEmpty()) {
            String objectName = String.format("installers/%s_%s", UUID.randomUUID(), installerFile.getOriginalFilename());
            installerUrl = gcsStorageService.uploadAsset(objectName, installerFile);
        }

        Product product = Product.builder()
                .productId(requestDTO.getProductId())
                .name(requestDTO.getName())
                .category(requestDTO.getCategory())
                .description(requestDTO.getDescription())
                .version(requestDTO.getVersion())
                .features(requestDTO.getFeatures())
                .pricingTiers(requestDTO.getPricingTiers())
                .gcsManualUrl(manualUrl)
                .gcsInstallerUrl(installerUrl)
                .status("ACTIVE")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductResponseDTO.class);
    }

    public ProductResponseDTO getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public ProductResponseDTO getProductByProductId(String productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with productId: " + productId));
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public List<ProductSummaryDTO> getAllProducts(String status, String category) {
        List<Product> products;
        if (status != null && !status.isBlank()) {
            products = productRepository.findByStatus(status);
        } else if (category != null && !category.isBlank()) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(p -> modelMapper.map(p, ProductSummaryDTO.class))
                .collect(Collectors.toList());
    }

    public ProductResponseDTO updateProduct(String id, ProductUpdateDTO updateDTO, MultipartFile manualFile, MultipartFile installerFile) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        if (updateDTO.getName() != null) product.setName(updateDTO.getName());
        if (updateDTO.getCategory() != null) product.setCategory(updateDTO.getCategory());
        if (updateDTO.getDescription() != null) product.setDescription(updateDTO.getDescription());
        if (updateDTO.getVersion() != null) product.setVersion(updateDTO.getVersion());
        if (updateDTO.getStatus() != null) product.setStatus(updateDTO.getStatus());
        if (updateDTO.getFeatures() != null) product.setFeatures(updateDTO.getFeatures());
        if (updateDTO.getPricingTiers() != null) product.setPricingTiers(updateDTO.getPricingTiers());

        if (manualFile != null && !manualFile.isEmpty()) {
            String objectName = String.format("manuals/%s_%s", UUID.randomUUID(), manualFile.getOriginalFilename());
            product.setGcsManualUrl(gcsStorageService.uploadAsset(objectName, manualFile));
        }

        if (installerFile != null && !installerFile.isEmpty()) {
            String objectName = String.format("installers/%s_%s", UUID.randomUUID(), installerFile.getOriginalFilename());
            product.setGcsInstallerUrl(gcsStorageService.uploadAsset(objectName, installerFile));
        }

        product.setUpdatedAt(Instant.now());
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductResponseDTO.class);
    }

    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
