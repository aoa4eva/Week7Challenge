package me.afua.week7challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UncheckedIOException;
import java.util.ArrayList;

@Service
public class NewsService {

    @Value("${apiKey}")
    private String apiKey;

    @Autowired
    AppUserRepository userRepo;



    public News getStories(String from, String category, int returnResults)
    {
        News theNews = new News();

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
        return theNews;
    }

    public News getStories(String from, String category)
    {
        return getStories(from,category,6);
    }

    public News getTopStories()
    {

        return getStories("top-headlines","general",3);
    }

    public News getTopicNews(String topic)
    {
        String from = "everything";
        RestTemplate restTemplate = new RestTemplate();
        News theNews = restTemplate.getForObject("https://newsapi.org/v2/"+from+"?country=us&q="+topic+"&apiKey="+apiKey,News.class);
        for(Article eachItem : theNews.getArticles())
        {
            eachItem.setCategory(topic);
        }
        return theNews;
    }
    public ArrayList <MyNewsDisplay> getUserNewsDisplay(Authentication auth)
    {
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







}
