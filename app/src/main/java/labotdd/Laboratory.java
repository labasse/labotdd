package labotdd;

import java.util.HashMap;
import java.util.Map;

public class Laboratory {
    public Laboratory(String[] substances) {
        if(substances.length == 0) {
            throw new IllegalArgumentException("Substance list cannot be empty");
        }
        for(var s : substances) {
            if(s == null || s.isEmpty() || containsSubstance(s)) {
                throw new IllegalArgumentException("Substance cannot be null, empty or duplicate: " + s);
            }
            substanceList.put(s, 0.0);
        }
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
        if(!containsSubstance(substance) || qty < 0) {
            throw new IllegalArgumentException("Unknown substance (" + substance + ") or negative quantity (" + qty + ")");
        }
        substanceList.put(substance, qty);
    }
    private Map<String, Double> substanceList = new HashMap<>();
}
