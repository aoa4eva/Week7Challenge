package me.afua.week7challenge;


import java.util.ArrayList;

public class MyNewsDisplay {

    String category;

    News categorisedNews;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public News getCategorisedNews() {
        return categorisedNews;
    }

    public void setCategorisedNews(News categorisedNews) {
        this.categorisedNews = categorisedNews;
    }

    @Override
    public String toString() {
        return "MyNewsDisplay{" +
                "category='" + category + '\'' +
                ", categorisedNews=" + categorisedNews +
                '}';
    }
}
