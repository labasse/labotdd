package labotdd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTest {
    public Laboratory newLaboratory(String... substances) {
        return new Laboratory(substances, new HashMap<>());
    }
    public Laboratory newLaboratoryAB(String... reactions) {
        return new Laboratory(new String[]{"A", "B"}, parseReactions(reactions));
    }
    public Map<@NonNull String, List<Laboratory.@NonNull Reagent>> parseReactions(String... reactions) {
        Map<@NonNull String, List<Laboratory.@NonNull Reagent>> reactionMap = new HashMap<>();

        for (String reaction : reactions) {
            List<Laboratory.@NonNull Reagent> reagents = new java.util.ArrayList<>();
            String[] 
                parts = reaction.split(" = "), 
                reagentsParts = parts.length < 2 ? new String[]{""} : parts[1].split(" \\+ ");
            String product = parts[0];

            for (String reagentPart : reagentsParts) {
                String[] info = reagentPart.split(" ");
                String name;
                double quantity;
                if(info.length < 2) {
                    name = info[0];
                    quantity = 1.0;
                }
                else {
                    name = info[1];
                    quantity = Double.parseDouble(info[0]);
                }
                reagents.add(new Laboratory.Reagent(name, quantity));
            }
            reactionMap.put(product+"", reagents);
        }
        return reactionMap;
    }

    public void addABC(Laboratory lab, double qtyA, double qtyB, double qtyC) {
        lab.add("A", qtyA);
        lab.add("B", qtyB);
        lab.add("C", qtyC);
    }

    public void assertQuantityAB(Laboratory lab, double expectedQtyForA, double expectedQtyForB) {
        assertEquals(expectedQtyForA, lab.getQuantity("A"), .0001);
        assertEquals(expectedQtyForB, lab.getQuantity("B"), .0001);
    }
    public void assertQuantityABCD(Laboratory lab, 
        double expectedQtyForA, double expectedQtyForB, 
        double expectedQtyForC, double expectedQtyForD) {
        assertQuantityAB(lab, expectedQtyForA, expectedQtyForB);
        assertEquals(expectedQtyForC, lab.getQuantity("C"), .0001);
        assertEquals(expectedQtyForD, lab.getQuantity("D"), .0001);
    }


    @Test void initWithEmptySubstanceListThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratory()
        );
    }

    @Test void initWithRegularSubstanceList() {
        var test = newLaboratoryAB();

        assertQuantityAB(test, 0.0, 0.0);
        assertThrows(IllegalArgumentException.class, () -> test.getQuantity("C"));
    }

    @Test void initWithNullSubstanceListThrowsNullReferenceException() {
        assertThrows(NullPointerException.class, 
            () -> new Laboratory(null, new HashMap<>())
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

    @Test void initWithEmptyReactionName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Laboratory(new String[]{"A", "B"}, Map.of(
                "", List.of(new Laboratory.Reagent("A", 1.0))
            ))
        );
    }

    @Test void initWithEmptyReagentList() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratoryAB("C = ")
        );
    }

    @Test void initWithNullReactionMap() {
        assertThrows(NullPointerException.class, () ->
            new Laboratory(new String[]{"A", "B"}, null)
        );
    }

    @Test void initWithProductInItsOwnReagentListThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratoryAB("C = C + 2 A")
        );
    }

    @Test void initWithNegativeQuantityInReagentThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratoryAB("C = -1 A")
        );
    }

    @Test void initWithZeroQuantityInReagentThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratoryAB("C = 0 A")
        );
    }

    @Test void initWithProductNamedLikeASubstanceThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratoryAB("A = B")
        );
    }

    @Test void initWithNotExistingSubstanceThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            newLaboratoryAB("A = X")
        );
    }

    @Test void initRegularReactions() {
        var test = newLaboratoryAB("C = 2 A + B", "D = A");

        assertQuantityABCD(test, 
            0.0, 0.0,
            0.0, 0.0
        );
    }

    @Test void initWithProductAsReagentOfOtherProduct() {
        var test = newLaboratoryAB("C = A", "D = 2 C");
        
        assertQuantityABCD(test, 
            0.0, 0.0,
            0.0, 0.0
        );}

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
        assertQuantityAB(test, 0.0, 0.0);
    }

    @Test void addQuantityOnce() {
        var test = newLaboratoryAB();

        test.add("A", 2);
        assertQuantityAB(test, 2.0, 0.0);
    }

    @Test void addQuantityMultipleTimes() {
        var test = newLaboratoryAB();

        test.add("A", 2);
        test.add("A", 3);
        assertQuantityAB(test, 5.0, 0.0);
    }

    @Test void addQuantityMultipleSubstance() {
        var test = newLaboratoryAB();

        test.add("A", 2.0);
        test.add("B", 0.5);
        assertQuantityAB(test, 2.0, 0.5);
    }

    @Test void addProductOnce() {
        var test = newLaboratoryAB("C = 2 A + B", "D = A");

        test.add("C", 4.0);
        assertQuantityABCD(test,
            0.0, 0.0, 
            4.0, 0.0
        );
    }

    @Test void makeUnknownProductThrowsIllegalArgumentException() {
        var test = newLaboratoryAB();

        assertThrows(IllegalArgumentException.class, () ->
            test.make("C", 1.0)
        );
    }

    @Test void makeSubstanceThrowsIllegalArgumentException() {
        var test = newLaboratoryAB();

        assertThrows(IllegalArgumentException.class, () ->
            test.make("A", 1.0)
        );
    }

    @Test void makeProductWithZeroQuantityThrowsIllegalArgumentException() {
        var test = newLaboratoryAB("C = 2 A + B", "D = A");

        assertThrows(IllegalArgumentException.class, () ->
            test.make("C", 0.0)
        );
    }

    @Test void makeProductWithNegativeQuantityThrowsIllegalArgumentException() {
        var test = newLaboratoryAB("C = 2 A + B", "D = A");

        assertThrows(IllegalArgumentException.class, () ->
            test.make("C", -1.0)
        );
    }

    @Test void makeProductWithSufficientReagents() {
        var test = newLaboratoryAB("C = 2 A + B", "D = A");

        addABC(test, 5.2, 4.1, 0);
    
        double made = test.make("C", 2.0);
        assertEquals(2.0, made, .0001);
        assertQuantityABCD(test,
            1.2, 2.1,
            2.0, 0.0
        );
    }

    @Test void makeProductWithStoredQtyAndSufficientReagents() {
        var test = newLaboratoryAB("C = 2 A + B", "D = A");

        addABC(test, 5.2, 4.1, 1.3);

        double made = test.make("C", 2.0);
        
        assertEquals(2.0, made, .0001);
        assertQuantityABCD(test,
            1.2, 2.1,
            3.3, 0.0
        );
    }

    @Test void makeProductWithInsufficientReagents() {
        var test = newLaboratoryAB("C = 2 A + B", "D = A");

        addABC(test, 3.5, 1.2, 0.3);
    
        double made = test.make("C", 2.0);
        assertEquals(1.2, made, .0001);
        assertQuantityABCD(test,
            1.1, 0.0,
            1.5, 0.0
        );
    }

    @Test void makeUsingStoredProductAsReagent() {
        var test = newLaboratoryAB("C = A", "D = 2 C + A");

        addABC(test, 5.0, 0.0, 5.0);
    
        double made = test.make("D", 2.0);

        assertEquals(2, made, .0001);
        assertQuantityABCD(test,
            3.0, 0.0,
            1.0, 2.0
        );
    }

    @Test void makeUsingRecursiveReactionForReagentProduct() {
        var test = newLaboratoryAB("C = A", "D = 2 C + A");

        addABC(test, 6.0, 0.0, 1.0);
    
        double made = test.make("D", 2.0);

        assertEquals(2, made, .0001);
        assertQuantityABCD(test,
            1.0, 0.0,
            0.0, 2.0
        );
    }
}
