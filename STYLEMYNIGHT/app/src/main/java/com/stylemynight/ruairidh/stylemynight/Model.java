package com.stylemynight.ruairidh.stylemynight;

//https://www.youtube.com/watch?v=ub6mNHWGVHw&fbclid=IwAR2FGapffEfh_wU3rtSSUbYvyNbsU0BApON0Ho0dTxgioNWmPPwKz0KaqvE

public class Model {

    String company, image, link, look, title;
    float price;


    public Model() {

    }

    public String getLink() {
        return link;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLook() {
        return look;
    }

    public void setLook(String look) {
        this.look = look;
    }

    public float getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
