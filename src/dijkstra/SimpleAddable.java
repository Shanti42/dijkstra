package dijkstra;

import java.math.BigInteger;
import java.util.Objects;

public class SimpleAddable implements Addable{
    BigInteger value;


    private SimpleAddable(BigInteger value) {
        this.value = value;
    }

    public static Addable of(int value) {
        return new SimpleAddable(BigInteger.valueOf(value));
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
        if(a instanceof SimpleAddable) {
            return SimpleAddable.of(value.add(((SimpleAddable) a).value));
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SimpleAddable) {
            return value.equals(((SimpleAddable) obj).value);
        }
        return false;
    }


    // for testing
    @Override
    public String toString() {
        return value.toString();
    }
}
