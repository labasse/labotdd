package labotdd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTest {
    @Test void initWithEmptySubstanceListThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            new Laboratory(new String[]{})
        );
    }

    @Test void initWithRegularSubstanceList() {
        var test = new Laboratory(new String[]{
            "A", "B"
        });
        assertEquals(0, test.getQuantity("A"));
        assertEquals(0, test.getQuantity("B"));
        assertThrows(IllegalArgumentException.class, () -> test.getQuantity("C"));
    }

    @Test void initWithNullSubstanceListThrowsNullReferenceException() {
        assertThrows(NullPointerException.class, 
            () -> new Laboratory(null)
        );
    }

    @Test void initWithDuplicateSubstanceListThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            new Laboratory(new String[]{
                "A", "B", "A"
            })
        );
    }
}
