// CAITLIN DO THIS!!! YOU FOOL!!!

package dijkstra;

import org.junit.Test;
import java.math.BigInteger;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


//import static junit.framework.TestCase.assertEquals;

public class PathNodeTest {
    private Cost<Addable> cost1 = Cost.of(SimpleAddable.of(BigInteger.valueOf(1)));
    private Cost<Addable> cost2 = Cost.of(SimpleAddable.of(BigInteger.valueOf(2)));

    private Node node1 = Node.of("ABC", cost1);
    private Node node2 = Node.of("BCD", cost2);


    private PathNode pathNode1 = PathNode.of(node1, cost1);
    private PathNode pathNode2 = PathNode.of(node2, cost2, pathNode1);

    @Test
    public void getNode_Test() {
        assertEquals(pathNode1.getNode(), node1);
    }

    @Test
    public void getCost_Test() {
        assertEquals(pathNode1.getCost(), cost1);
    }

    @Test
    public void getTotalCost_Test() {
        Cost cost_a = pathNode2.getTotalCost();
        Cost cost_b = cost1.plus(cost2);
        assertEquals(cost_a.cost(), cost_b.cost());
        assertEquals(pathNode1.getTotalCost(), cost1);
    }


}