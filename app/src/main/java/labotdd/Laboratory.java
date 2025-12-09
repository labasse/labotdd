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
            if(product.isEmpty() || reagents.isEmpty() || substanceList.containsKey(product)) {
                throw new IllegalArgumentException(
                    "Reaction name or reagent list cannot be empty or product cannot be a known substance: " + product
                );
            }
            for(var r : reagents) {
                if(r.name.equals(product) || !substanceList.containsKey(r.name) || r.quantity <= 0) {
                    throw new IllegalArgumentException(
                        "Reaction cannot have its product or unknown substance as reagent " + r.name + 
                        "or non-positive reagent quantity" + r.name + ", " + r.quantity
                    );
                }
            }
            reactionMap.put(product, List.copyOf(reagents));
            substanceList.put(product, 0.0);
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
        addNoCheck(substance, qty);
    }
    public void addNoCheck(String substance, double qty) {
        substanceList.put(substance, getQuantity(substance) + qty);
    }
    public double make(@NonNull String product, double qty) {
        if(!reactionMap.containsKey(product) || qty <= 0) {
            throw new IllegalArgumentException("product must be known and quantity must be positive or zero");
        }
        var make = Math.min(qty, reactionMap.get(product).stream()
            .mapToDouble(r -> {
                var possible = getQuantity(r.name)/r.quantity;
                
                if(possible<qty && reactionMap.containsKey(r.name)) {
                    possible += make(r.name, (qty - possible) * r.quantity)/r.quantity;
                } 
                return possible;
            })
            .min()
            .orElse(0));
        
        reactionMap.get(product).forEach(
            r -> addNoCheck(r.name, - r.quantity * make)
        );
        addNoCheck(product, make);
        return make;
    }

    private final Map<String, Double> substanceList = new HashMap<>();
    private final Map<@NonNull String, List<@NonNull Reagent>> reactionMap = new HashMap<>();
}
