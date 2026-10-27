import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class MyCollections {

    private MyCollections() {

    }

    public static <T> void copy(MyCollection<? super T> dest, MyCollection<? extends T> src) {
        for (T item : src) {
            dest.add(item);
        }
    }

    public static double sum(MyCollection<? extends Number> c) {
        double total = 0;
        for (Number n : c) {
            total += n.doubleValue();
        }
        return total;
    }

    public static <T extends Comparable<? super T>> T max(MyCollection<? extends T> c) {
        Iterator<? extends T> it = c.iterator();
        if (!it.hasNext()) throw new NoSuchElementException("Collection is empty");
        T best = it.next();
        while (it.hasNext()) {
            T candidate = it.next();
            if (candidate.compareTo(best) > 0) best = candidate;
        }
        return best;
    }

    public static void print(String label, MyCollection<?> c) {
        System.out.println(label + Arrays.toString(c.toArray()));
    }
}
