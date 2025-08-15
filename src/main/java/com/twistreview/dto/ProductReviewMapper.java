package com.twistreview.dto;

import com.twistreview.model.ProductReview;

public class ProductReviewMapper {
    public static ProductReviewDTO toDTO(ProductReview review) {
        return new ProductReviewDTO(
            review.getId(),
            review.getProductId(),
            review.getUserName(),
            review.getRating(),
            review.getTitle(),
            review.getComment(),
            review.getDate(),
            review.isVerified(),
            review.getHelpful(),
            review.getAvatar()
        );
    }

    public static ProductReview toEntity(ProductReviewDTO dto) {
        return new ProductReview(
            dto.getId(),
            dto.getProductId(),
            dto.getUserName(),
            dto.getRating(),
            dto.getTitle(),
            dto.getComment(),
            dto.getDate(),
            dto.isVerified(),
            dto.getHelpful(),
            dto.getAvatar()
        );
    }
}
