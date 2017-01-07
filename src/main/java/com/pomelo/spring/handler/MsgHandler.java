package com.pomelo.spring.handler;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pomelo.spring.builder.NewsBuilder;
import com.pomelo.spring.cache.NewsCache;
import com.pomelo.spring.spider.SpiderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pomelo.spring.service.WeixinService;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Autowired
    SpiderService spiderService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {

        WeixinService weixinService = (WeixinService) wxMpService;

        if (!wxMessage.getMsgType().equals(WxConsts.XML_MSG_EVENT)) {
            // TODO 可以选择将消息保存到本地
        }

        // 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")) {
            return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
        }

        // TODO 组装回复消息
        if (CollectionUtils.isEmpty(NewsCache.listNews)) {
            logger.info("开始扫描");
            spiderService.execute();
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                if (CollectionUtils.isNotEmpty(NewsCache.listNews)) {
                    logger.info("prepare news item = {}", JSON.toJSONString(NewsCache.listNews));
                    break;
                }
            }
        }

        if (StringUtils.equals("刷新音乐", wxMessage.getContent())) {
            NewsCache.listMusics.clear();
        }
        if (StringUtils.equals("刷新文章", wxMessage.getContent())) {
            NewsCache.listNews.clear();
        }

        if (StringUtils.equals("音乐", wxMessage.getContent())) {
            if (CollectionUtils.isEmpty(NewsCache.listMusics)) {
                logger.info("开始扫描音乐");
                spiderService.executeMusic();
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    if (CollectionUtils.isNotEmpty(NewsCache.listMusics)) {
                        logger.info("prepare music item = {}", JSON.toJSONString(NewsCache.listMusics));
                        break;
                    }
                }
            }
            logger.info("返回音乐:{}", JSON.toJSONString(NewsCache.listMusics));
            return new NewsBuilder().build(NewsCache.listMusics, wxMessage);
        }

        return new NewsBuilder().build(NewsCache.listNews, wxMessage);

    }

}
