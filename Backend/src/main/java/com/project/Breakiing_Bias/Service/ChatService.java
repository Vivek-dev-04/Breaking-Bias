package com.project.Breakiing_Bias.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Breakiing_Bias.DTO.Article;
import com.project.Breakiing_Bias.DTO.ChatResponse;
import com.project.Breakiing_Bias.DTO.NewsResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {
    @Autowired
    private NewsService newsService;
    private ChatClient client;

    public ChatService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    public String analyze(String newsText) {
        NewsResponse response = newsService.fetchNews(newsText);

        // ← handle empty articles
        if (response.getArticles() == null || response.getArticles().isEmpty()) {
            System.out.println("=== NO ARTICLES FOUND, using general knowledge ===");
            return analyzeWithoutArticles(newsText);
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> list = new ArrayList<>();
        for (Article a : response.getArticles()) {
            Map<String, String> m = new HashMap<>();
            m.put("title", safe(a.getTitle()));
            m.put("description", safe(a.getDescription()));
            m.put("content", safe(a.getContent()));
            m.put("author", safe(a.getAuthor()));
            m.put("source", a.getSource() != null ? safe(a.getSource().getName()) : "");
            m.put("publishedAt", safe(a.getPublishedAt()));
            m.put("url", safe(a.getUrl()));
            list.add(m);
        }

        String json = "";
        try {
            json = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(json);

        return client.prompt(
                        "You are a news fact-checking AI.\n" +
                                "Your job is to verify the following CLAIM.\n\n" +
                                "CLAIM TO VERIFY: " + newsText + "\n\n" +  // ← claim first
                                "Use the following current news articles as context:\n" +
                                "Articles: " + json + "\n\n" +
                                "Return ONLY valid JSON:\n" +
                                "{\n" +
                                "  \"classification\": \"REAL | FAKE | PARTIALLY_TRUE | UNCERTAIN | MISLEADING\",\n" +
                                "  \"confidence\": 0-100,\n" +
                                "  \"facts\": [\"key facts that support or contradict the claim\"],\n" +
                                "  \"reason\": \"explanation of verdict based on claim and articles\",\n" +
                                "  \"indicators\": [\"specific signs supporting or contradicting the claim\"]\n" +
                                "}\n\n" +
                                "Rules:\n" +
                                "- Focus ONLY on verifying the CLAIM\n" +
                                "- Use articles as supporting context\n" +
                                "- Do NOT analyze articles themselves\n" +
                                "- Mark UNCERTAIN if no relevant articles found\n" +
                                "- Avoid 100% confidence unless certain\n"
                )
                .call()
                .content()
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }

    // ← fallback when no articles found
    private String analyzeWithoutArticles(String newsText) {
        return client.prompt(
                        "You are a news fact-checking AI.\n" +
                                "No current news articles found. Use your general knowledge to verify.\n\n" +
                                "CLAIM TO VERIFY: " + newsText + "\n\n" +
                                "Return ONLY valid JSON:\n" +
                                "{\n" +
                                "  \"classification\": \"REAL | FAKE | PARTIALLY_TRUE | UNCERTAIN | MISLEADING\",\n" +
                                "  \"confidence\": 0-100,\n" +
                                "  \"facts\": [\"key facts about the claim\"],\n" +
                                "  \"reason\": \"explanation based on general knowledge\",\n" +
                                "  \"indicators\": [\"supporting or contradicting signs\"]\n" +
                                "}\n"
                )
                .call()
                .content()
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }

    private String safe(String s) {
        return s != null ? s : "";
    }
}