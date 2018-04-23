package com.jonaszwiacek.siker.Siker.Searchers;

public class Item {
    private String title;
    private String imageSource;
    private String price;

    public Item(String title, String imageSource, String price) {
        this.title = title;
        this.imageSource = imageSource;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String toJSON() {
        return "{" +
                " \"title\": \"" + title + "\", " +
                " \"price\": \"" + price + "\", " +
                " \"img\": \"" + imageSource + "\"" +
                "}";
    }
}
