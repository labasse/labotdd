package labotdd;

import java.util.Set;
import java.util.HashSet;

public class Laboratory {
    public Laboratory(String[] substances) {
        if(substances.length == 0) {
            throw new IllegalArgumentException("Substance list cannot be empty");
        }
        for(var s : substances) {
            substanceList.add(s);
        }
    }
    public double getQuantity(String substance) {
        if(!substanceList.contains(substance)) {
            throw new IllegalArgumentException("Substance not found: " + substance);
        }
        return 0;
    }
    private Set<String> substanceList = new HashSet<>();
}
