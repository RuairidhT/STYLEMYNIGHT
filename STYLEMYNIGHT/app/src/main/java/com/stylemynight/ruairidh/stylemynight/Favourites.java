package com.stylemynight.ruairidh.stylemynight;

public class Favourites {

    String users_id, title, price, image, link, company;

    public String getCompany() {
        return company;
    }

    public Favourites() {

    }

    public String getUsers_id() {
        return users_id;
    }

    public void setUsers_id(String users_name) {
        this.users_id = users_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
