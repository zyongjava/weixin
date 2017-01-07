package com.pomelo.spring.handler;

import java.util.Map;

import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class NullHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager) {

        WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content("输入:nginx").fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
        return m;
    }

}
