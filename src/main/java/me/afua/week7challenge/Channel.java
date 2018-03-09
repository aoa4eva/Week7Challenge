package me.afua.week7challenge;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="channel")
public class Channel {
    //@XmlElement(name="item")
    private ArrayList<NewsItem> items;


    public Channel(){
        items = new ArrayList();
    }

    public ArrayList<NewsItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<NewsItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "items=" + items +
                '}';
    }
}
