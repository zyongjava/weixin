
## 使用步骤：
1. 配置: 复制/src/main/resources/wx.properties.template 生成 wx.properties 文件，填写相关配置;		
1. 使用maven运行demo程序: `mvn jetty:run`  或者自己打war包发布到tomcat运行；
1. 配置微信公众号中的接口地址：http://xxx/wechat/portal （注意XXX需要是外网可访问的域名，需要符合微信官方的要求）；
1. 根据自己需要修改各个handler的实现，加入自己的业务逻辑。
	
