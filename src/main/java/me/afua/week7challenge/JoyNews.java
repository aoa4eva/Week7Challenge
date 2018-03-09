package me.afua.week7challenge;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class JoyNews {
    @XmlElement(name="channel")
    private Channel channel;

    @JsonCreator
    public JoyNews() {
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
