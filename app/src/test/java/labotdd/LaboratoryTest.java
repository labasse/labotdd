package labotdd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTest {
    public Laboratory newLaboratory(String... substances) {
        return new Laboratory(substances);
    }
    public Laboratory newLaboratoryAB() {
        return new Laboratory(new String[]{"A", "B"});
    }
    public void asserQuantityAB(Laboratory lab, double expectedQtyForA, double expectedQtyForB) {
        assertEquals(expectedQtyForA, lab.getQuantity("A"), .0001);
        assertEquals(expectedQtyForB, lab.getQuantity("B"), .0001);
    }


    @Test void initWithEmptySubstanceListThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratory()
        );
    }

    @Test void initWithRegularSubstanceList() {
        var test = newLaboratoryAB();

        asserQuantityAB(test, 0.0, 0.0);
        assertThrows(IllegalArgumentException.class, () -> test.getQuantity("C"));
    }

    @Test void initWithNullSubstanceListThrowsNullReferenceException() {
        assertThrows(NullPointerException.class, 
            () -> new Laboratory(null)
        );
    }

    @Test void initWithDuplicateSubstanceListThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratory("A", "B", "A")
        );
    }

    @Test void initWithListHavingEmptyStringSubstanceThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratory("A", "")
        );
    }

    @Test void initWithListHavingNullStringSubstanceThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratory("A", null)
        );
    }

    @Test void addUnknownSubstanceThrowsIllegalArgumentException() {
        var test = newLaboratoryAB();
        
        assertThrows(IllegalArgumentException.class, () ->
            test.add("C", .5)
        );
    }

    @Test void addKnownSubstanceWithNegativeQuantityThrowsIllegaleArgumentException() {
        var test = newLaboratoryAB();
        
        assertThrows(IllegalArgumentException.class, () ->
            test.add("A", -0.5)
        );
    }

    @Test void addZeroQuantityDoesNotThrow() {
        var test = newLaboratoryAB();

        test.add("A", 0);
        assertEquals(0, test.getQuantity("A"), .0001);
    }

    @Test void addQuantityOnce() {
        var test = newLaboratoryAB();

        test.add("A", 2);
        assertEquals(2.0, test.getQuantity("A"), .0001);
    }

    @Test void addQuantityMultipleTimes() {
        var test = newLaboratoryAB();

        test.add("A", 2);
        test.add("A", 3);
        asserQuantityAB(test, 5.0, 0.0);
    }

    @Test void addQuantityMultipleSubstance() {
        var test = newLaboratoryAB();
        
        test.add("A", 2.0);
        test.add("B", 0.5);
        asserQuantityAB(test, 2.0, 0.5);
    }
}
