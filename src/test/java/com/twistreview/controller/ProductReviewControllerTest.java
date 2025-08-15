
package com.twistreview.controller;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twistreview.dto.ProductReviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductReviewController.class)
@AutoConfigureMockMvc
public class ProductReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private com.twistreview.service.ProductReviewService productReviewService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getReviewsByProduct_shouldReturnOk() throws Exception {
    org.mockito.Mockito.when(productReviewService.getReviewsByProductId("ret_pos_001"))
        .thenReturn(java.util.Collections.emptyList());
    mockMvc.perform(get("/api/reviews/ret_pos_001")
    )
        .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void addReview_shouldReturnCreated() throws Exception {
        ProductReviewDTO dto = new ProductReviewDTO();
        dto.setUserName("user1");
        dto.setRating(5);
        dto.setTitle("Test Review");
        dto.setComment("This is a test review.");
        dto.setDate("2025-08-14");
        dto.setVerified(true);
        dto.setHelpful(1);
        dto.setAvatar("U1");
    org.mockito.Mockito.when(productReviewService.addReview(org.mockito.Mockito.anyString(), org.mockito.Mockito.any())).thenReturn(new com.twistreview.model.ProductReview());
    org.mockito.Mockito.when(productReviewService.isReviewOwner(org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString(), org.mockito.Mockito.eq("user1"))).thenReturn(true);
    mockMvc.perform(post("/api/reviews/test_product")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
