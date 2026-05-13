package com.project.Breakiing_Bias.Controller;

import com.project.Breakiing_Bias.DTO.Article;
import com.project.Breakiing_Bias.DTO.ChatRequest;
import com.project.Breakiing_Bias.DTO.NewsResponse;
import com.project.Breakiing_Bias.Service.NewsService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public NewsResponse getNews(@RequestBody ChatRequest query) {
        return newsService.fetchNews(query.getRequest());
    }
}