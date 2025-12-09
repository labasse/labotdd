package labotdd;

import java.util.HashMap;
import java.util.Map;

import org.jspecify.annotations.NonNull;

import java.util.List;

public class Laboratory {
    public record Reagent(String name, double quantity) {}

    public Laboratory(String[] substances, Map<@NonNull String, List<@NonNull Reagent>> reactions) {
        if(reactions == null) {
            throw new NullPointerException("Reactions map cannot be null");
        }
        if(substances.length == 0) {
            throw new IllegalArgumentException("Substance list cannot be empty");
        }
        for(var s : substances) {
            if(s == null || s.isEmpty() || containsSubstance(s)) {
                throw new IllegalArgumentException("Substance cannot be null, empty or duplicate: " + s);
            }
            substanceList.put(s, 0.0);
        }
        reactions.forEach((product, reagents) -> {
            if(product.isEmpty()) {
                throw new IllegalArgumentException("Reaction name cannot be empty");
            }
            if(reagents.isEmpty()) {
                throw new IllegalArgumentException("Reagent list cannot be empty for reaction: " + product);
            }
        });
    }
    private boolean containsSubstance(String substance) {
        return substanceList.containsKey(substance);
    }
    public double getQuantity(String substance) {
        if(!containsSubstance(substance)) {
            throw new IllegalArgumentException("Substance not found: " + substance);
        }
        return substanceList.get(substance);
    }
    public void add(String substance, double qty) {
        if(qty < 0) {
            throw new IllegalArgumentException("Negative quantity");
        }
        substanceList.put(substance, getQuantity(substance) + qty);
    }
    private Map<String, Double> substanceList = new HashMap<>();
}
