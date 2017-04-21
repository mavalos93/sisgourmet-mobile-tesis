package tesis.com.py.sisgourmetmobile.entities;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "ORDER".
 */
public class Order {

    private Long id;
    private String orderType;
    private String statusDescription;
    private Integer statusOrder;
    private Integer drinksId;
    private Integer lunchId;
    private Integer drinkDetails;
    private Integer launchDetails;
    private java.util.Date createdAt;
    private Double orderAmount;

    public Order() {
    }

    public Order(Long id) {
        this.id = id;
    }

    public Order(Long id, String orderType, String statusDescription, Integer statusOrder, Integer drinksId, Integer lunchId, Integer drinkDetails, Integer launchDetails, java.util.Date createdAt, Double orderAmount) {
        this.id = id;
        this.orderType = orderType;
        this.statusDescription = statusDescription;
        this.statusOrder = statusOrder;
        this.drinksId = drinksId;
        this.lunchId = lunchId;
        this.drinkDetails = drinkDetails;
        this.launchDetails = launchDetails;
        this.createdAt = createdAt;
        this.orderAmount = orderAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Integer getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(Integer statusOrder) {
        this.statusOrder = statusOrder;
    }

    public Integer getDrinksId() {
        return drinksId;
    }

    public void setDrinksId(Integer drinksId) {
        this.drinksId = drinksId;
    }

    public Integer getLunchId() {
        return lunchId;
    }

    public void setLunchId(Integer lunchId) {
        this.lunchId = lunchId;
    }

    public Integer getDrinkDetails() {
        return drinkDetails;
    }

    public void setDrinkDetails(Integer drinkDetails) {
        this.drinkDetails = drinkDetails;
    }

    public Integer getLaunchDetails() {
        return launchDetails;
    }

    public void setLaunchDetails(Integer launchDetails) {
        this.launchDetails = launchDetails;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

}
