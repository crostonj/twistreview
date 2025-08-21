package com.twistreview.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twistreview.TwistreviewApplication;
import com.twistreview.model.ProductReview;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class ImportReviewsUtility {
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(TwistreviewApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext ctx = app.run("--spring.profiles.active=utility");
        try {
            ObjectMapper objectMapper = ctx.getBean(ObjectMapper.class);
            MongoTemplate mongoTemplate = ctx.getBean(MongoTemplate.class);
            Environment env = ctx.getEnvironment();

            String input = env.getProperty("utility.input", "classpath:data/productReview.json");
            boolean drop = Boolean.parseBoolean(env.getProperty("utility.dropCollection", "false"));

            Resource resource = input.startsWith("classpath:")
                    ? new ClassPathResource(input.substring("classpath:".length()))
                    : new FileSystemResource(input);
            if (!resource.exists()) {
                System.err.printf("Input not found at '%s'. Nothing to import.%n", input);
                return;
            }

            try (InputStream is = resource.getInputStream()) {
                List<ProductReview> reviews = objectMapper.readValue(is, new TypeReference<List<ProductReview>>() {});
                if (drop) {
                    try {
                        mongoTemplate.dropCollection(ProductReview.class);
                        System.out.println("Dropped existing 'productReviews' collection.");
                    } catch (Exception e) {
                        System.err.printf("Failed to drop collection: %s%n", e.getMessage());
                    }
                }
                int success = 0;
                for (ProductReview r : reviews) {
                    if (r.getProductId() == null || r.getProductId().isBlank()) {
                        System.err.printf("Skipping review with missing productId, id=%s%n", r.getId());
                        continue;
                    }
                    if (r.getId() == null || r.getId().isBlank()) {
                        r.setId(UUID.randomUUID().toString());
                    }
                    try {
                        mongoTemplate.save(r);
                        success++;
                    } catch (Exception ex) {
                        System.err.printf("Failed to save review id=%s productId=%s: %s%n", r.getId(), r.getProductId(), ex.getMessage());
                    }
                }
                System.out.printf("Imported/updated %d reviews into '%s' collection.%n", success, "productReviews");
            }
        } finally {
            ctx.close();
        }
    }
}
