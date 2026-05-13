package com.project.Breakiing_Bias.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClaimProcessingServices {
    public String refineQuery(String claim) {
        // ← null check first
        if (claim == null || claim.trim().isEmpty()) {
            return "latest news";
        }

        Set<String> stopwords = Set.of(
                "is","was","are","the","a","an","in","on","at","to","of",
                "for","and","with","that","this","has","have","had","did",
                "does","do","be","been","being","will","can","may","mr","mrs",
                "dr","current","currently","now","its","their","our","he","she"
        );

        String cleaned = claim.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase().trim();

        String result = Arrays.stream(cleaned.split("\\s+"))
                .filter(w -> w.length() > 2)
                .filter(w -> !stopwords.contains(w))
                .collect(Collectors.joining(" "));

        System.out.println("=== REFINED QUERY: " + result);
        return result.isEmpty() ? claim : result;
    }
}