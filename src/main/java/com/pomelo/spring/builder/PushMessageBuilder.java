package com.pomelo.spring.builder;

import com.pomelo.spring.service.WeixinService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by zhengyong on 16/12/31.
 */
@Component("pushMessageBuilder")
public class PushMessageBuilder {

    @Autowired
    private WeixinService wxService;

    public void build (WxMpKefuMessage.WxArticle article) throws WxErrorException {

        // og9RmuM7FXs2ag5Y3XEXO4B6eCoA
        // og9RmuEB0ImHUGjTLfo5yqfr9k44
        // toUser ; gh_3038f326d95f 公共号
        WxMpKefuMessage message = WxMpKefuMessage.NEWS()
                .toUser("gh_3038f326d95f")
                .addArticle(article)
                .build();

        wxService.getKefuService().sendKefuMessage(message);

    }
}
