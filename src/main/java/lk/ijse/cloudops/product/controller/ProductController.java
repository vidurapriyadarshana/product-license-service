package lk.ijse.cloudops.product.controller;

import jakarta.validation.Valid;
import lk.ijse.cloudops.product.dto.request.ProductRequestDTO;
import lk.ijse.cloudops.product.dto.request.ProductUpdateDTO;
import lk.ijse.cloudops.product.dto.response.ProductResponseDTO;
import lk.ijse.cloudops.product.dto.response.ProductSummaryDTO;
import lk.ijse.cloudops.product.dto.response.StandardResponse;
import lk.ijse.cloudops.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StandardResponse<ProductResponseDTO>> createProduct(
            @Valid @RequestPart("product") ProductRequestDTO requestDTO,
            @RequestPart(value = "manual", required = false) MultipartFile manualFile,
            @RequestPart(value = "installer", required = false) MultipartFile installerFile) {
        ProductResponseDTO created = productService.createProduct(requestDTO, manualFile, installerFile);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StandardResponse.success(HttpStatus.CREATED.value(), "Product created successfully", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<ProductResponseDTO>> getProductById(@PathVariable String id) {
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "Product retrieved successfully", product));
    }

    @GetMapping("/code/{productId}")
    public ResponseEntity<StandardResponse<ProductResponseDTO>> getProductByProductId(@PathVariable String productId) {
        ProductResponseDTO product = productService.getProductByProductId(productId);
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "Product retrieved successfully", product));
    }

    @GetMapping
    public ResponseEntity<StandardResponse<List<ProductSummaryDTO>>> getAllProducts(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category) {
        List<ProductSummaryDTO> products = productService.getAllProducts(status, category);
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "Products retrieved successfully", products));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StandardResponse<ProductResponseDTO>> updateProduct(
            @PathVariable String id,
            @RequestPart("product") ProductUpdateDTO updateDTO,
            @RequestPart(value = "manual", required = false) MultipartFile manualFile,
            @RequestPart(value = "installer", required = false) MultipartFile installerFile) {
        ProductResponseDTO updated = productService.updateProduct(id, updateDTO, manualFile, installerFile);
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "Product updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse<Void>> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(StandardResponse.success(HttpStatus.OK.value(), "Product deleted successfully", null));
    }
}
