package tesis.com.py.sisgourmetmobile.models;

import java.util.ArrayList;

import tesis.com.py.sisgourmetmobile.entities.Lunch;

/**
 * Created by Manu0 on 25/4/2017.
 */

public class SectionDataModel {

    private String headerTitle;
    private ArrayList<Lunch> allItemsInSection;


    public SectionDataModel() {

    }

    public SectionDataModel(String headerTitle, ArrayList<Lunch> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }


    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Lunch> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Lunch> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

}
