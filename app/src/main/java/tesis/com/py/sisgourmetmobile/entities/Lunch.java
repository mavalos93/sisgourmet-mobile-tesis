package tesis.com.py.sisgourmetmobile.entities;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import java.io.Serializable;

/**
 * Entity mapped to table "LUNCH".
 */
public class Lunch implements Serializable{

    private Long id;
    private Integer priceUnit;
    private String mainMenuDescription;
    private Long providerId;
    private java.util.Date menuDate;
    private Long ratingMenu;

    public Lunch() {
    }

    public Lunch(Long id) {
        this.id = id;
    }

    public Lunch(Long id, Integer priceUnit, String mainMenuDescription, Long providerId, java.util.Date menuDate, Long ratingMenu) {
        this.id = id;
        this.priceUnit = priceUnit;
        this.mainMenuDescription = mainMenuDescription;
        this.providerId = providerId;
        this.menuDate = menuDate;
        this.ratingMenu = ratingMenu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Integer priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getMainMenuDescription() {
        return mainMenuDescription;
    }

    public void setMainMenuDescription(String mainMenuDescription) {
        this.mainMenuDescription = mainMenuDescription;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public java.util.Date getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(java.util.Date menuDate) {
        this.menuDate = menuDate;
    }

    public Long getRatingMenu() {
        return ratingMenu;
    }

    public void setRatingMenu(Long ratingMenu) {
        this.ratingMenu = ratingMenu;
    }

}
