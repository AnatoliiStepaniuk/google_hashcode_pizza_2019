import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class PizzaReaderTest {

    @Test
    public void readTest() throws IOException {
        PizzaReader reader = new PizzaReader("/Users/anatolii.stepaniuk/Education/google_hashcode_pizza_2019/src/test/java/input.txt");
        Pizza actual = reader.read();

        boolean[][] expectedIsMushroom = new boolean[][]{
                {false, false, false, false, false},
                {false, true, true, true, false},
                {false, false, false, false, false},
        };

        Assert.assertEquals(1, actual.minEach);
        Assert.assertEquals(6, actual.maxSliceSize);
        for(int i = 0; i < 3; i++) {
            Assert.assertTrue(Arrays.equals(expectedIsMushroom[i], actual.isMushroom[i]));
        }
    }
}
