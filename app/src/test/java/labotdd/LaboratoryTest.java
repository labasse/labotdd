package labotdd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTest {
    @Test void initWithEmptySubstanceListThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            new Laboratory(new String[]{})
        );
    }
}
