package labotdd;

import java.util.Set;
import java.util.HashSet;

public class Laboratory {
    public Laboratory(String[] substances) {
        if(substances.length == 0) {
            throw new IllegalArgumentException("Substance list cannot be empty");
        }
        for(var s : substances) {
            if(s == null || s.isEmpty() || substanceList.contains(s)) {
                throw new IllegalArgumentException("Substance cannot be null, empty or duplicate: " + s);
            }
            substanceList.add(s);
        }
    }
    public double getQuantity(String substance) {
        if(!substanceList.contains(substance)) {
            throw new IllegalArgumentException("Substance not found: " + substance);
        }
        return 0;
    }
    public void add(String substance, double qty) {
        throw new IllegalArgumentException("Unknown substance (" + substance + ") or negative quantity (" + qty + ")");
    }
    private Set<String> substanceList = new HashSet<>();
}
