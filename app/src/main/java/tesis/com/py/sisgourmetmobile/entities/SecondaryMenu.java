package tesis.com.py.sisgourmetmobile.entities;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "SECONDARY_MENU".
 */
public class SecondaryMenu {

    private Long id;
    private Integer priceUnit;
    private String description;

    public SecondaryMenu() {
    }

    public SecondaryMenu(Long id) {
        this.id = id;
    }

    public SecondaryMenu(Long id, Integer priceUnit, String description) {
        this.id = id;
        this.priceUnit = priceUnit;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
