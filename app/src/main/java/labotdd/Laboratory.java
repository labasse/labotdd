package labotdd;

public class Laboratory {
    public Laboratory(String[] substances) {
        throw new IllegalArgumentException("Substance list cannot be empty");
    }
    public double getQuantity(String substance) {
        return -1.0;
    }
}
