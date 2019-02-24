import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class AreaTest {

    @Test
    public void positionsTest(){
        Area area = new Area(new Position(2,3), new Position(3, 5));
        Collection<Position> expected = new HashSet<>();
        expected.add(new Position(2,3));
        expected.add(new Position(2,4));
        expected.add(new Position(2,5));
        expected.add(new Position(3,3));
        expected.add(new Position(3,4));
        expected.add(new Position(3,5));

        List<Position> actual = area.positions();
        Assert.assertEquals(6, actual.size());
        Assert.assertTrue(actual.containsAll(expected));
    }

    @Test
    public void indexToPositionTest(){
        Area area = new Area(new Position(0, 0), new Position(2, 4));
        Position expected = new Position(2, 0);
        Position actual = area.indexToPosition(10);
        Assert.assertEquals(expected, actual);
    }
}
