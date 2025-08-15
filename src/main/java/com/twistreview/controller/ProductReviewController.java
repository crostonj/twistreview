
package com.twistreview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.access.prepost.PreAuthorize;

import com.twistreview.model.ProductReview;
import com.twistreview.service.ProductReviewService;
import com.twistreview.dto.ProductReviewDTO;
import com.twistreview.dto.ProductReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Product Reviews", description = "Endpoints for managing product reviews")
public class ProductReviewController {
    @Autowired
    private ProductReviewService productReviewService;

    @PutMapping("/{productId}/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or #reviewDTO.userName == authentication.name")
    @Operation(summary = "Update a review", description = "Update a review by product and review ID. Only admins or the review owner can update.")
    public ResponseEntity<ProductReviewDTO> updateReview(
            @Parameter(description = "Product ID") @PathVariable String productId,
            @Parameter(description = "Review ID") @PathVariable String reviewId,
            @Parameter(description = "Review DTO") @RequestBody ProductReviewDTO reviewDTO) {
        reviewDTO.setProductId(productId);
        reviewDTO.setId(reviewId);
        ProductReview updated = productReviewService.updateReview(productId, reviewId, ProductReviewMapper.toEntity(reviewDTO));
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ProductReviewMapper.toDTO(updated));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get reviews for a product", description = "Retrieve all reviews for a given product ID.")
    public ResponseEntity<List<ProductReviewDTO>> getReviewsByProduct(
            @Parameter(description = "Product ID") @PathVariable String productId) {
        List<ProductReview> reviews = productReviewService.getReviewsByProductId(productId);
        if (reviews == null || reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ProductReviewDTO> dtos = reviews.stream().map(ProductReviewMapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{productId}")
    @Operation(summary = "Add a review", description = "Add a new review for a product.")
    public ResponseEntity<ProductReviewDTO> addReview(
            @Parameter(description = "Product ID") @PathVariable String productId,
            @Parameter(description = "Review DTO") @RequestBody ProductReviewDTO reviewDTO) {
        reviewDTO.setProductId(productId);
        ProductReview saved = productReviewService.addReview(productId, ProductReviewMapper.toEntity(reviewDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductReviewMapper.toDTO(saved));
    }

    @DeleteMapping("/{productId}/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or @productReviewService.isReviewOwner(#productId, #reviewId, authentication.name)")
    @Operation(summary = "Delete a review", description = "Delete a review by product and review ID. Only admins or the review owner can delete.")
    public ResponseEntity<Void> deleteReview(
            @Parameter(description = "Product ID") @PathVariable String productId,
            @Parameter(description = "Review ID") @PathVariable String reviewId) {
        boolean deleted = productReviewService.deleteReview(productId, reviewId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
