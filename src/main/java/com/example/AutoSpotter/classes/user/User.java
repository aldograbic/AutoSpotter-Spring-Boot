package com.example.AutoSpotter.classes.user;


import com.example.AutoSpotter.classes.location.City;
import com.example.AutoSpotter.classes.location.County;
import com.example.AutoSpotter.config.AuthenticationType;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String companyName;
    private String companyOIB;
    private String address;
    private String phoneNumber;
    private String email;
    private String profileImage;
    private boolean emailVerified;
    private String confirmationToken;
    private int cityId;

    private City city;
    private County county;
    private boolean acceptedTermsOfService;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyOIB() {
        return companyOIB;
    }

    public void setCompanyOIB(String companyOIB) {
        this.companyOIB = companyOIB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    private AuthenticationType authType;
 
    public AuthenticationType getAuthType() {
        return authType;
    }
 
    public void setAuthType(AuthenticationType authType) {
        this.authType = authType;
    }


    public User(String username, String password, String firstName, String lastName, String companyName, String companyOIB, String address, String phoneNumber, String email, int cityId) {
        
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.companyOIB = companyOIB;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.cityId = cityId;
    }

    public User() {}
}
