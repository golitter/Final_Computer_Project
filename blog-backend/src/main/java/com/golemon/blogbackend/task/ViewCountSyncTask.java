package com.golemon.blogbackend.task;

import com.golemon.blogbackend.common.domain.entity.Article;
import com.golemon.blogbackend.enums.BlogSystemConstantsEnum;
import com.golemon.blogbackend.service.ArticleService;
import com.golemon.blogbackend.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountSyncTask {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0 */3 * * ?") // Execute every 3 hours
//@Scheduled(cron = "0 0/2 * * * ?") // Execute every 2 minutes
public void syncViewCountToDb() {
        // Get view count data from Redis
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(BlogSystemConstantsEnum.REDIS_ARTICLE_VIEW_COUNT_KEY.getValue());
        
        // Update to database
        List<Article> articles = viewCountMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), Long.valueOf(entry.getValue())))
                .collect(Collectors.toList());
        
        if (!articles.isEmpty()) {
            articleService.updateBatchById(articles);
        }
    }
} 