package labotdd;

import java.util.List;
import java.util.ArrayList;

public class Laboratory {
    public Laboratory(String[] substances) {
        if(substances.length == 0) {
            throw new IllegalArgumentException("Substance list cannot be empty");
        }
        for(var s : substances) {
            if(substanceList.contains(s)) {
                throw new IllegalArgumentException("Duplicate substance: " + s);
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
    private List<String> substanceList = new ArrayList<>();
}
