package com.pomelo.spring.cache;

import java.util.Vector;

/**
 * Created by zhengyong on 16/12/30.
 */
public class NewsCache {

    public static Vector<News> listNews = new Vector<>();

    public static Vector<News> listMusics = new Vector<>();

    public static News createNews() {
        return new News();
    }

    public static class News {

        private String title;
        private String time;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
