package com.example.hong.boaaproject.mainActivity;

public class UserInformationModel {


    String userNicName;
    String userHeight;
    String userWeight;
    String userImgURL;

    public UserInformationModel(String userNicName, String userHeight, String userWeight, String userImgURL) {

        this.userNicName = userNicName;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userImgURL = userImgURL;
    }

    public String getUserNicName() {
        return userNicName;
    }

    public void setUserNicName(String userNicName) {
        this.userNicName = userNicName;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public String getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(String userWeight) {
        this.userWeight = userWeight;
    }

    public String getUserImgURL() {
        return userImgURL;
    }

    public void setUserImgURL(String userImgURL) {
        this.userImgURL = userImgURL;
    }
}
