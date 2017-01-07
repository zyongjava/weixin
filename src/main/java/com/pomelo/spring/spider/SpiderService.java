package com.pomelo.spring.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;

/**
 * 基于webmagic爬取csdn
 */
@Component("spiderService")
public class SpiderService {

    @Resource(name = "csdnProcessor")
    private PageProcessor csdnProcessor;

    @Resource(name = "musicProcessor")
    private PageProcessor musicProcessor;

    public static Logger  logger = LoggerFactory.getLogger(SpiderService.class);

    public void execute() {

        Spider.create(csdnProcessor)
              // 从url开始抓
              .addUrl("http://blog.csdn.net/" + CsdnProcessor.CSDN_URI)
              // 设置Scheduler，使用File来管理URL队列
              // .setScheduler(new FileCacheQueueScheduler("/app/queue"))
              // 设置Pipeline，将结果以json方式保存到文件
              .addPipeline(new ConsolePipeline())
              // 开启5个线程同时执行
              .thread(5)
              // 启动爬虫
              .run();
    }

    public void executeMusic() {
        Spider.create(musicProcessor)
              // 从url开始抓
              .addUrl("http://music.163.com/m/playlist?id=455563168&userid=325465165#?thirdfrom=qq")
              // 设置Scheduler，使用File来管理URL队列
              // .setScheduler(new FileCacheQueueScheduler("/app/queue"))
              // 设置Pipeline，将结果以json方式保存到文件
              .addPipeline(new ConsolePipeline())
              // 开启5个线程同时执行
              .thread(5)
              // 启动爬虫
              .run();

    }

    public static void main(String[] args) {
        Spider.create(new MusicProcessor())
              // 从url开始抓
              .addUrl("http://music.163.com/m/playlist?id=455563168&userid=325465165#?thirdfrom=qq")
              // 设置Scheduler，使用File来管理URL队列
              // .setScheduler(new FileCacheQueueScheduler("/app/queue"))
              // 设置Pipeline，将结果以json方式保存到文件
              .addPipeline(new ConsolePipeline())
              // 开启5个线程同时执行
              .thread(5)
              // 启动爬虫
              .run();
    }
}
