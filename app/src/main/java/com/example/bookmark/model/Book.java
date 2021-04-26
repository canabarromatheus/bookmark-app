package com.example.bookmark.model;

import java.util.Arrays;
import java.util.List;

public class Book {

    private String userUUID;
    private int imageId;
    private String title;
    private List<String> gender;
    private String edition;
    private int year;
    private String synopsis;
    private String userAddress;

    public Book() {
    }

    public Book(String userUUID, int imageId, String title, List<String> gender, String edition, int year, String synopsis, String userAddress) {
        this.userUUID = userUUID;
        this.imageId = imageId;
        this.title = title;
        this.gender = gender;
        this.edition = edition;
        this.year = year;
        this.synopsis = synopsis;
        this.userAddress = userAddress;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString() {
        return "Book{" +
                "userUUID='" + userUUID + '\'' +
                ", imageId=" + imageId +
                ", title='" + title + '\'' +
                ", gender=" + gender +
                ", edition='" + edition + '\'' +
                ", year=" + year +
                ", synopsis='" + synopsis + '\'' +
                ", userAddress='" + userAddress + '\'' +
                '}';
    }
}
