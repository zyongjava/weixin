package com.pomelo.spring.spider;

import java.util.List;

import com.pomelo.spring.cache.NewsCache;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * csdn 下载控制
 */
@Component("csdnProcessor")
public class CsdnProcessor implements PageProcessor {

    public static Logger       logger   = LoggerFactory.getLogger(CsdnProcessor.class);

    private Site               site     = Site.me().setSleepTime(0).setCycleRetryTimes(3);

    /**
     * csdn uri 后缀
     */
    public static final String CSDN_URI = "zhengyong15984285623";

    @Override
    public void process(Page page) {
        List pagenation = page.getHtml().links().regex("/" + CSDN_URI + "/article/list/\\d*").all();
        page.addTargetRequests(pagenation);
        // 里面页面只捕捉url加入爬取队列
        if (CollectionUtils.isNotEmpty(pagenation)) {
            List<String> titleList = page.getHtml().xpath("//div[@id='article_list']/div[@class=list_item]").all();
            for (String titleHtml : titleList) {
                page.addTargetRequests(new Html(titleHtml).links().regex("/" + CSDN_URI
                                                                         + "/article/details/\\d*").all());
            }
            page.setSkip(true);
        } else { // csdn具体文章页面
            String title = page.getHtml().xpath("//div[@class=article_title]/h1/span/a/text()").toString();
            String createTime = page.getHtml().xpath("//div[@class=article_r]/span[@class=link_postdate]/text()").toString();
            page.putField("title", title);
            page.putField("createTime", createTime);
            page.putField("url", page.getUrl().toString());

            NewsCache.News news = NewsCache.createNews();
            news.setTitle(title);
            news.setTime(createTime);
            news.setUrl(page.getUrl().toString());
            NewsCache.listNews.add(news);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
