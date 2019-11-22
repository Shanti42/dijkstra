package dijkstra;

import java.math.BigInteger;
import java.util.Objects;

public class SimpleAddable implements Addable{
    BigInteger value;


    private SimpleAddable(BigInteger value) {
        this.value = value;
    }

    public static Addable of(BigInteger value){
        Objects.requireNonNull(value, "SimpleAddable, of -> value null");
        return new SimpleAddable(value);
    }



    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public Addable plus(Addable a) {
        return null;
    }
}
