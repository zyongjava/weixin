package com.pomelo.spring.builder;

import java.util.Random;
import java.util.Vector;

import com.pomelo.spring.cache.NewsCache;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMusicMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * Created by zhengyong on 16/12/30.
 */
public class MusicBuilder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public WxMpXmlOutMessage build(Vector<NewsCache.News> news, WxMpXmlMessage wxMessage) {

        logger.info("build music={} , wxMessage{}", JSON.toJSONString(news), JSON.toJSONString(wxMessage));

        me.chanjar.weixin.mp.builder.outxml.MusicBuilder builder = WxMpXmlOutMessage.MUSIC();

        int size = news.size();
        int index = randomIndex(size);
        builder.title(news.get(index).getTitle()).musicUrl(news.get(index).getUrl()).thumbMediaId("200395662");
        WxMpXmlOutMusicMessage m = builder.fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();

        return m;
    }

    private int randomIndex(int size) {
        Random rand = new Random();
        int r = rand.nextInt(size);
        return r;
    }
}
