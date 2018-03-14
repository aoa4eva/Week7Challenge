package me.afua.week7challenge;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@Service
public class NewsService {

    @Value("${apiKey}")
    private String apiKey;

    @Autowired
    AppUserRepository userRepo;


    public News getStories(String from, String category, int returnResults)
    {

        //Modify this to use a list of RSS feeds that a user has chosen

        MyNewsDisplay fromJoy = getGHNews("http://citifmonline.com/feed/");

        News theNews;

        RestTemplate restTemplate = new RestTemplate();
        try {
            theNews = restTemplate.getForObject("https://newsapi.org/v2/"+from+"?country=us&pageSize="+returnResults+"&category="+category+"&apiKey="+apiKey,News.class);

        }catch (Exception e)
        {
            throw new UsernameNotFoundException("Unable to find API");
        }

        if(theNews!=null)
        {
            for(Article eachItem : theNews.getArticles())
            {
                eachItem.setCategory(category);
            }
        }

        for (Article joyArticle: fromJoy.getCategorisedNews().getArticles())
        {
            joyArticle.setCategory(category);

        }

        theNews.addArticles(fromJoy.categorisedNews.getArticles());
        return theNews;
    }

    public News getStories(String from, String category)
    {
        return getStories(from,category,8);
    }

    public News getTopStories()
    {

        return getStories("top-headlines","general",3);
    }

    public News getTopicNews(String topic)
    {
        String from = "everything";
        String topicurl = "https://newsapi.org/v2/"+from+"?q="+topic+"&apiKey="+apiKey;

        RestTemplate restTemplate = new RestTemplate();
        News theNews = restTemplate.getForObject(topicurl,News.class);
        for(Article eachItem : theNews.getArticles())
        {
            eachItem.setCategory(topic);
        }
        return theNews;
    }

    public ArrayList <MyNewsDisplay> getUserNewsDisplay(Authentication auth)
    {
        System.out.println(auth.getName());
        ArrayList<MyNewsDisplay> myDisplay = new ArrayList<>();

        AppUser thisUser = userRepo.findByUsername(auth.getName());
        for(String eachCategory:thisUser.getCategories())
        {
            MyNewsDisplay newItem = new MyNewsDisplay();
            newItem.setCategorisedNews(getStories("top-headlines",eachCategory));
            newItem.setCategory(eachCategory);
            myDisplay.add(newItem);
        }
        return myDisplay;
    }


    public ArrayList <MyNewsDisplay> getUserTopicDisplay(Authentication auth)
    {
        System.out.println(auth.getName());
        ArrayList<MyNewsDisplay> myDisplay = new ArrayList<>();

        AppUser thisUser = userRepo.findByUsername(auth.getName());
        for(String eachTopic:thisUser.getTopics())
        {
            MyNewsDisplay newItem = new MyNewsDisplay();
            newItem.setCategorisedNews(getTopicNews(eachTopic));
            newItem.setCategory(eachTopic);
            myDisplay.add(newItem);
        }
        return myDisplay;
    }

    public MyNewsDisplay getGHNews(String ghNewsLink)
    {
        MyNewsDisplay generalGHNews = new MyNewsDisplay();
        News ghNewsArticles = new News();
        URL ghNewsURL = null;
        ArrayList <Article> ghArticles = new ArrayList<>();
        Article anArticle = null;

        try {
            ghNewsURL = new URL(ghNewsLink);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = new SyndFeedImpl();
        try {
            feed = input.build(new com.rometools.rome.io.XmlReader(ghNewsURL));
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(SyndEntry eachFeed:feed.getEntries())
        {
            anArticle = new Article();
            anArticle.setCategory("general");
            anArticle.setDescription(eachFeed.getDescription().getValue());
            anArticle.setTitle(eachFeed.getTitle());
            anArticle.setPublishedAt(eachFeed.getPublishedDate().toString());
            anArticle.setUrl(eachFeed.getLink());

            ghArticles.add(anArticle);

     }
        ghNewsArticles.setArticles(ghArticles);
        generalGHNews.setCategory("general");
        generalGHNews.setCategorisedNews(ghNewsArticles);
        return generalGHNews;
    }









}
