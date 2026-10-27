public interface MyCollection<E> extends Iterable<E> {
    boolean add(E e);

    boolean addAll(MyCollection<? extends E> c);

    boolean contains(Object o);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    Object[] toArray();
}
