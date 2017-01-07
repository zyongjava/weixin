package com.pomelo.spring.handler;

import java.util.Map;

import com.pomelo.spring.builder.TextBuilder;
import org.springframework.stereotype.Component;

import com.pomelo.spring.service.WeixinService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang
 */
@Component
public class SubscribeHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        WeixinService weixinService = (WeixinService) wxMpService;
        try {
            return new TextBuilder().build("感谢关注,输入'音乐'听歌", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {
        // TODO
        return null;
    }

}
