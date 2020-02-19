package com.example.foodapp;

public class ProvidersClass {

        private String address;
        private String avatarImage;
        private String cuisine;
        private String deliveryRadius;
        private String email;
        private String meal;
        private String phone;
        private String serviceName;
        private String uid;

    public ProvidersClass(String address, String avatarImage, String cuisine, String deliveryRadius, String email, String meal, String phone, String serviceName, String uid) {
        this.address = address;
        this.avatarImage = avatarImage;
        this.cuisine = cuisine;
        this.deliveryRadius = deliveryRadius;
        this.email = email;
        this.meal = meal;
        this.phone = phone;
        this.serviceName = serviceName;
        this.uid = uid;
    }

    public ProvidersClass() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getDeliveryRadius() {
        return deliveryRadius;
    }

    public void setDeliveryRadius(String deliveryRadius) {
        this.deliveryRadius = deliveryRadius;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
