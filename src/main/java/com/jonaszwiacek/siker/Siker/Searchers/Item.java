package com.jonaszwiacek.siker.Siker.Searchers;

public class Item {
    private String title;
    private String imageSource;
    private String price;
    private String link;
    private String site;

    public Item(String title, String imageSource, String price, String link, String site) {
        this.title = title;
        this.imageSource = imageSource;
        this.price = price;
        this.link = link;
        this.site = site;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String toJSON() {
        return "{" +
                " \"title\": \"" + title + "\", " +
                " \"price\": \"" + price + "\", " +
                " \"img\": \"" + imageSource + "\", " +
                " \"link\": \"" + link + "\", " +
                " \"site\": \"" + site + "\"" +
                "}";

    }
}