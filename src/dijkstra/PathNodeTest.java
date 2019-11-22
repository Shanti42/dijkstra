// CAITLIN DO THIS!!! YOU FOOL!!!

package dijkstra;

import org.junit.Before;
import org.junit.Test;
import java.math.BigInteger;
import org.junit.Assert;

import static org.junit.Assert.*;


//import static junit.framework.TestCase.assertEquals;

public class PathNodeTest {
    private Cost<Addable> cost1 = Cost.of(SimpleAddable.of(BigInteger.valueOf(1)));
    private Cost<Addable> cost2 = Cost.of(SimpleAddable.of(BigInteger.valueOf(2)));
    private Cost<Addable> cost3 = Cost.of(SimpleAddable.of(10));
    private Cost<Addable> cost4 = Cost.of(SimpleAddable.of(50));
    private Cost<Addable> cost5 = Cost.of(SimpleAddable.of(11));
    private Cost<Addable> cost6 = Cost.of(SimpleAddable.of(12));

    private Node node1 = Node.of("ABC", cost1);
    private Node node2 = Node.of("XYZ", cost2);
    private Node node3 = Node.of("MNO", cost3);

    private Connection connection1_2 = SimpleConnection.of(node1, node2, cost4);
    private Connection connection1_3 = SimpleConnection.of(node1, node3, cost5);
    private Connection connection2_3 = SimpleConnection.of(node2, node3, cost6);

    private PathNode pathNode1;
    private PathNode pathNode2;
    private PathNode pathNode3;

    private PathNode unknownCost = PathNode.of(node1);

    @Before
    public void setup() {



        pathNode1 = PathNode.of(node1, cost1);
        pathNode2 = PathNode.of(node2, cost2, pathNode1);
        pathNode3 = PathNode.of(node3, cost3, pathNode2);
    }

    @Test
    public void getNode_Test() {
        assertEquals(pathNode1.getNode(), node1);
    }

    @Test
    public void getCost_Test() {
        assertEquals(pathNode1.getCost(), cost1);
    }

    @Test
    public void getPrevious_Test() {
        assertNull(pathNode1.getPrevious());
        assertEquals(pathNode2.getPrevious(), pathNode1);
        assertEquals(pathNode3.getPrevious(), pathNode2);
    }

    @Test
    public void getTotalCost_Test() {
        assertEquals(pathNode1.getTotalCost(), cost1);
        assertEquals(pathNode2.getTotalCost(), cost1.plus(cost2));
        assertEquals(pathNode3.getTotalCost(), cost1.plus(cost2.plus(cost3)));
    }

    @Test
    public void availableNodes_Test() {

    }
}