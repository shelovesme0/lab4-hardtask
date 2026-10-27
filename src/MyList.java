public interface MyList<E> {
    void add(E e);
    void add(int index, E element);
    void addAll(E[] c);
    void addAll(int index, E[] c);
    E get(int index);
    E remove(int index);

    void set(int index, E element);

    int indexOf(Object o);

    int size();
    Object[] toArray();


    default void addAll(MyList<? extends E> c) {
        for (int i = 0; i < c.size(); i++) {
            add(c.get(i));
        }
    }
}
