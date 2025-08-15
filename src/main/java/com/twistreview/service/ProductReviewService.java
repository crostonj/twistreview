
package com.twistreview.service;

import com.twistreview.model.ProductReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean isReviewOwner(String productId, String reviewId, String username) {
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("id").is(reviewId).and("productId").is(productId).and("userName").is(username));
        return mongoTemplate.exists(query, ProductReview.class);
    }

    public ProductReview updateReview(String productId, String reviewId, ProductReview updatedReview) {
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("id").is(reviewId).and("productId").is(productId));
        ProductReview existing = mongoTemplate.findOne(query, ProductReview.class);
        if (existing == null) {
            return null;
        }
        updatedReview.setId(reviewId);
        updatedReview.setProductId(productId);
        mongoTemplate.save(updatedReview);
        return updatedReview;
    }

    public List<ProductReview> getReviewsByProductId(String productId) {
        return mongoTemplate.find(
            org.springframework.data.mongodb.core.query.Query.query(
                org.springframework.data.mongodb.core.query.Criteria.where("productId").is(productId)
            ),
            ProductReview.class
        );
    }

    public ProductReview addReview(String productId, ProductReview review) {
        review.setId(java.util.UUID.randomUUID().toString());
        mongoTemplate.save(review);
        return review;
    }

    public boolean deleteReview(String productId, String reviewId) {
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("id").is(reviewId).and("productId").is(productId));
        return mongoTemplate.remove(query, ProductReview.class).getDeletedCount() > 0;
    }
}
