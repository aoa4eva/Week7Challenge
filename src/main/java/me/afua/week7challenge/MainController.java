package me.afua.week7challenge;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

;

@Controller
public class MainController {
    @RequestMapping("/")
    public @ResponseBody  String showIndex()
    {
        RestTemplate restTemplate = new RestTemplate();
        News theNews = restTemplate.getForObject("https://newsapi.org/v2/top-headlines?q=bitcoin&apiKey=7950f30c312945d8856e4dcc09aec04b",News.class);
        for(Article eachArticle:theNews.getArticles())
        {
            System.out.println(eachArticle.getDescription());
        }

        //Jackson
        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.xml();
        builder.indentOutput(true);
        restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter(builder.build()));


        JoyNews joy = restTemplate.getForObject("http://www.myjoyonline.com/pages/rss/site_edition.xml",JoyNews.class);

        System.out.println("Joy is:"+joy+" with "+joy.getChannel().toString());
        for(NewsItem eachItem: joy.getChannel().getItems())
        {
            System.out.println("This item has been shown:");
            System.out.println(eachItem.getTitle());
        }
        return "The news has been retrieved";
    }

}
