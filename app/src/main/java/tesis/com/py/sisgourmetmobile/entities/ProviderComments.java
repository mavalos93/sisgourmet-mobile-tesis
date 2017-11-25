package tesis.com.py.sisgourmetmobile.entities;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PROVIDER_COMMENTS".
 */
public class ProviderComments {

    private Long id;
    private Integer commentId;
    private Integer commentRating;
    private String commentDescription;
    private String selectedMenu;
    private String userName;
    private String dateComment;
    private Integer providerId;

    public ProviderComments() {
    }

    public ProviderComments(Long id) {
        this.id = id;
    }

    public ProviderComments(Long id, Integer commentId, Integer commentRating, String commentDescription, String selectedMenu, String userName, String dateComment, Integer providerId) {
        this.id = id;
        this.commentId = commentId;
        this.commentRating = commentRating;
        this.commentDescription = commentDescription;
        this.selectedMenu = selectedMenu;
        this.userName = userName;
        this.dateComment = dateComment;
        this.providerId = providerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentRating() {
        return commentRating;
    }

    public void setCommentRating(Integer commentRating) {
        this.commentRating = commentRating;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public String getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(String selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateComment() {
        return dateComment;
    }

    public void setDateComment(String dateComment) {
        this.dateComment = dateComment;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

}
