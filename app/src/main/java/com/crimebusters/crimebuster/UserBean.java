package com.crimebusters.crimebuster;

/**
 * Created by awais on 6/6/2017.
 */

public class UserBean {
    int userId;
    String userFullname;
    int userCnic;
    String userCity;
    String userDob;
    String userEmail;
    String userPass;
    int userStatus;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public int getUserCnic() {
        return userCnic;
    }

    public void setUserCnic(int userCnic) {
        this.userCnic = userCnic;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public UserBean(int userId, String userFullname, int userCnic, String userCity, String userDob, String userEmail, String userPass, int userStatus) {
        this.userId = userId;
        this.userFullname = userFullname;
        this.userCnic = userCnic;
        this.userCity = userCity;
        this.userDob = userDob;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userStatus = userStatus;
    }
}
