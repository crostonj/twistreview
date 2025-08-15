package com.twistreview.dto;

public class ProductReviewDTO {
    private String id;
    private String productId;
    private String userName;
    private int rating;
    private String title;
    private String comment;
    private String date;
    private boolean verified;
    private int helpful;
    private String avatar;

    public ProductReviewDTO() {}

    public ProductReviewDTO(String id, String productId, String userName, int rating, String title, String comment, String date, boolean verified, int helpful, String avatar) {
        this.id = id;
        this.productId = productId;
        this.userName = userName;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.date = date;
        this.verified = verified;
        this.helpful = helpful;
        this.avatar = avatar;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public int getHelpful() { return helpful; }
    public void setHelpful(int helpful) { this.helpful = helpful; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
