package tesis.com.py.sisgourmetmobile.models;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Manu0 on 9/10/2017.
 */

public class ProviderQualificationModel implements Serializable{

    private String providerName;
    private String providerRating;
    private int providerMaxValue;
    private int totalUserComments;
    private byte[] fileArrayImage;



    public ProviderQualificationModel(){

    }


    public ProviderQualificationModel(String providerName, String providerRating, int providerMaxValue, int totalUserComments, byte[] fileArrayImage) {
        this.providerName = providerName;
        this.providerRating = providerRating;
        this.providerMaxValue = providerMaxValue;
        this.totalUserComments = totalUserComments;
        this.fileArrayImage = fileArrayImage;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderRating() {
        return providerRating;
    }

    public void setProviderRating(String providerRating) {
        this.providerRating = providerRating;
    }

    public int getProviderMaxValue() {
        return providerMaxValue;
    }

    public void setProviderMaxValue(int providerMaxValue) {
        this.providerMaxValue = providerMaxValue;
    }

    public int getTotalUserComments() {
        return totalUserComments;
    }

    public void setTotalUserComments(int totalUserComments) {
        this.totalUserComments = totalUserComments;
    }

    public byte[] getFileArrayImage() {
        return fileArrayImage;
    }

    public void setFileArrayImage(byte[] fileArrayImage) {
        this.fileArrayImage = fileArrayImage;
    }

    @Override
    public String toString() {
        return "ProviderQualificationModel{" +
                "providerName='" + providerName + '\'' +
                ", providerRating='" + providerRating + '\'' +
                ", providerMaxValue=" + providerMaxValue +
                ", totalUserComments=" + totalUserComments +
                ", fileArrayImage=" + Arrays.toString(fileArrayImage) +
                '}';
    }
}
