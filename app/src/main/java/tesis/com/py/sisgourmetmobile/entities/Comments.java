package tesis.com.py.sisgourmetmobile.entities;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "COMMENTS".
 */
public class Comments {

    private Long id;
    private Integer providerId;
    private String userName;
    private String lunchPackageDescription;
    private Integer ratingValue;
    private String dateComment;
    private String commentDescription;

    public Comments() {
    }

    public Comments(Long id) {
        this.id = id;
    }

    public Comments(Long id, Integer providerId, String userName, String lunchPackageDescription, Integer ratingValue, String dateComment, String commentDescription) {
        this.id = id;
        this.providerId = providerId;
        this.userName = userName;
        this.lunchPackageDescription = lunchPackageDescription;
        this.ratingValue = ratingValue;
        this.dateComment = dateComment;
        this.commentDescription = commentDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLunchPackageDescription() {
        return lunchPackageDescription;
    }

    public void setLunchPackageDescription(String lunchPackageDescription) {
        this.lunchPackageDescription = lunchPackageDescription;
    }

    public Integer getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getDateComment() {
        return dateComment;
    }

    public void setDateComment(String dateComment) {
        this.dateComment = dateComment;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

}