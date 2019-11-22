package dijkstra;

public interface Addable extends Comparable{
    public int compareTo(Object o);
    public Addable plus(Addable a);
}
