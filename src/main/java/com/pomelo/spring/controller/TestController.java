package com.pomelo.spring.controller;

import com.pomelo.spring.service.WeixinService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhengyong on 16/12/30.
 */
@Controller
public class TestController {

    @Autowired
    private WeixinService wxService;

    @RequestMapping(value = "/ok.htm")
    @ResponseBody
    public String ok() {
        // 明文传输的消息
        WxMpXmlMessage inMessage = new WxMpXmlMessage();
        inMessage.setTitle("11");
        inMessage.setDeviceId("setDeviceId");
        inMessage.setUrl("222");
        inMessage.setFromUser("from");
        inMessage.setMsg("msg");
        inMessage.setMsgType("text");
        inMessage.setToUser("toUser");
        WxMpXmlOutMessage outMessage = wxService.route(inMessage);
        if (outMessage == null) {
            return "failed";
        }
        return "ok";
    }

}
