package com.pomelo.spring.builder;

import com.alibaba.fastjson.JSON;
import com.pomelo.spring.cache.NewsCache;
import com.google.common.collect.Sets;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Set;
import java.util.Vector;

/**
 * Created by zhengyong on 16/12/30.
 */
public class NewsBuilder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public WxMpXmlOutMessage build(Vector<NewsCache.News> news, WxMpXmlMessage wxMessage) {

        logger.info("build news={} , wxMessage{}", JSON.toJSONString(news), JSON.toJSONString(wxMessage));

        me.chanjar.weixin.mp.builder.outxml.NewsBuilder builder = WxMpXmlOutMessage.NEWS();

        String content = wxMessage.getContent();

        int i = 0;
        for (NewsCache.News it : news) {
            if (it.getTitle().contains(content)) {
                WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
                item.setTitle(it.getTitle());
                item.setUrl(it.getUrl());
                builder.addArticle(item);
                i++;
            }
            if (i > 5) {
                break;
            }
        }

        Set<Integer> set = Sets.newHashSet();
        if (i == 0) {
            int size = news.size();
            while (set.size() < 5) {
                int index = randomIndex(size);

                if (set.contains(index)) {
                    continue;
                }
                set.add(index);

                NewsCache.News it = news.get(index);
                WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
                item.setTitle(it.getTitle());
                item.setUrl(it.getUrl());
                builder.addArticle(item);
            }
        }

        WxMpXmlOutNewsMessage m = builder.fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();

        return m;
    }

    private int randomIndex(int size) {
        Random rand = new Random();
        int r = rand.nextInt(size);
        return r;
    }
}
