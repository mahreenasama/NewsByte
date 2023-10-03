package com.a2.newsbyte.config;


import com.a2.newsbyte.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@EnableAsync
@EnableScheduling
@Configuration
public class ScheduleConfiguration {
    @Autowired
    NewsService newsService;

    @Async
    @Scheduled( fixedDelay = 300000)
    public void scrappNews() throws IOException {
        System.out.println("run after every 5 minutes");            // 1 sec = 1000 ms, 5 min = 60 x 5 = 300 sec, 300 sec = 300000 ms
        newsService.runScrapper();
    }

}

