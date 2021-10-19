package in.mcxiv.jpsd.data.resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ImageResourceIDTest {

    @Test
    void testUniqueness() {
        ImageResourceID[] values = ImageResourceID.values();
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i].getIdentity() == values[j].getIdentity())
                    Assertions.fail();
            }
        }
    }
}