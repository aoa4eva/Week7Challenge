package me.afua.week7challenge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties
public class News {
    private ArrayList<Article> articles;


    public News() {
        articles = new ArrayList<>();
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "News{" +
                "articles=" + articles +
                '}';
    }
}
