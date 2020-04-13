package com.example.foodapp.ui.main;

public class CustomerSubscribed {
    private String uId;
    private String profileImg;
    private String firstName;
    private String lastName;
    private String email;
    private String deliveryAddress;
    private String packageSubscribedName;
    private String packagePrice;
    private String paymentID;
    private String paymentDate;
    private String paymentState;

    public CustomerSubscribed(String uId, String profileImg, String firstName, String lastName, String email, String deliveryAddress, String packageSubscribedName, String packagePrice, String paymentID, String paymentDate, String paymentState) {
        this.uId = uId;
        this.profileImg = profileImg;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
        this.packageSubscribedName = packageSubscribedName;
        this.packagePrice = packagePrice;
        this.paymentID = paymentID;
        this.paymentDate = paymentDate;
        this.paymentState = paymentState;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPackageSubscribedName() {
        return packageSubscribedName;
    }

    public void setPackageSubscribedName(String packageSubscribedName) {
        this.packageSubscribedName = packageSubscribedName;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }
}
