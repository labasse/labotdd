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

    @Test void initWithListHavingEmptyStringSubstanceThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            new Laboratory(new String[]{
                "A", ""
            })
        );
    }

    @Test void initWithListHavingNullStringSubstanceThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            new Laboratory(new String[]{
                "A", null
            })
        );
    }

    @Test void addUnknownSubstanceThrowsIllegalArgumentException() {
        var test = new Laboratory(new String[]{
            "A", "B"
        });
        assertThrows(IllegalArgumentException.class, () ->
            test.add("C", .5)
        );
    }

    @Test void addKnownSubstanceWithNegativeQuantityThrowsIllegaleArgumentException() {
        var test = new Laboratory(new String[]{
            "A", "B"
        });
        assertThrows(IllegalArgumentException.class, () ->
            test.add("A", -0.5)
        );
    }

    @Test void addZeroQuantityDoesNotThrow() {
        var test = new Laboratory(new String[]{
            "A", "B"
        });
        test.add("A", 0);
        assertEquals(0, test.getQuantity("A"));
    }

    @Test void addQuantityOnce() {
        var test = new Laboratory(new String[]{
            "A", "B"
        });
        test.add("A", 2);
        assertEquals(2.0, test.getQuantity("A"));
    }

}
