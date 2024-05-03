package com.symon.linkedn;

public class User {
    private String name, email, userId,gender,shortBio, skills;
    private String userImage;
    Long mobileNo;

    public User(String name, String email, String userId, String gender, String shortBio, String skills, String userImage, Long mobileNo) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.gender = gender;
        this.shortBio = shortBio;
        this.skills = skills;
        this.userImage = userImage;
        this.mobileNo = mobileNo;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public User(String name, String email, String userId, String gender, Long mobileNo, String shortBio, String userImage) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.gender = gender;
        this.shortBio = shortBio;
        this.userImage = userImage;
        this.mobileNo = mobileNo;
    }
    public User(String name, String email, String userId, String gender, Long mobileNo,  String shortBio) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.gender = gender;
        this.shortBio = shortBio;
        this.mobileNo = mobileNo;
    }

    public User(){

    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getShortBio() {
        return shortBio;
    }

    public Long getMobileNo() {
        return mobileNo;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
