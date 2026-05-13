package com.project.Breakiing_Bias.Service;

import com.google.api.client.util.Value;
import com.project.Breakiing_Bias.DTO.Article;
import com.project.Breakiing_Bias.DTO.NewsResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private ClaimProcessingServices claimProcessingServices;

    private String apiKey="700984d2d1c840a1aaba3c27335c80a5";

    public NewsResponse fetchNews(String query) {

        try {
            String refined = claimProcessingServices.refineQuery(query);
            System.out.println(refined);
            String encodedQuery = URLEncoder.encode(refined, StandardCharsets.UTF_8);

            String url = "https://newsapi.org/v2/everything?q="
                    + encodedQuery
                    + "&sortBy=publishedAt&pageSize=5"
                    + "&apiKey=" + apiKey;

            System.out.println("URL: " + url);

            RestTemplate restTemplate = new RestTemplate();
            NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);

            if (response == null) {
                throw new RuntimeException("Response is NULL");
            }

            System.out.println("Status: " + response.getStatus());
            System.out.println("Total Results: " + response.getTotalResults());

            return response;

        } catch (Exception e) {
            e.printStackTrace();  // 🔥 THIS IS CRITICAL
            throw new RuntimeException("News API failed: " + e.getMessage(), e);
        }
    }
}