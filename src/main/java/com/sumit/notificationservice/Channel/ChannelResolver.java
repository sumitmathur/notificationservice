package com.sumit.notificationservice.Channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChannelResolver {

    private final Map<String, Channel> channelHashMap;

    @Autowired
    public ChannelResolver(List<Channel> channelList) {
        channelHashMap = new HashMap<>();
        channelList.forEach(a -> channelHashMap.put(a.channelName(), a));
    }

    public Channel getChannelByName(String channel) {
        return this.channelHashMap.get(channel);
    }
}
