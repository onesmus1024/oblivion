package com.example.oblivion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationGenerator {
    private Map<String, List<String>> recommendations;

    public RecommendationGenerator() {
        recommendations = new HashMap<>();
        recommendations.put("fair", Arrays.asList("product1", "product2", "product3"));
        recommendations.put("light", Arrays.asList("product4", "product5", "product6"));
        recommendations.put("light medium", Arrays.asList("product4", "product5", "product6"));
        recommendations.put("medium", Arrays.asList("product4", "product5", "product6"));
        recommendations.put("medium deep", Arrays.asList("product4", "product5", "product6"));
        recommendations.put("dark", Arrays.asList("product7", "product8", "product9"));
    }

    public List<String> generateRecommendations(String skin_tone, String allergy) {
        List<String> suitableProducts = recommendations.get(skin_tone);
        List<String> filteredProducts = new ArrayList<>();
        for (String product : suitableProducts) {
            if (!allergy.contains(product)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}
