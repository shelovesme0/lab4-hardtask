public class MyLinkedList<E> implements MyList<E> {
    private int size = 0;
    private Node<E> head;
    private Node<E> tail;

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = head;
            for (int i = 0; i < index; i++) x = x.next;
            return x;
        } else {
            Node<E> x = tail;
            for (int i = size - 1; i > index; i--) x = x.prev;
            return x;
        }
    }

    @Override
    public void add(E e) {
        final Node<E> t = tail;
        final Node<E> newNode = new Node<>(t, e, null);
        tail = newNode;
        if (t == null) head = newNode;
        else t.next = newNode;
        size++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        Node<E> x = node(index);
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) head = next;
        else { prev.next = next; x.prev = null; }

        if (next == null) tail = prev;
        else { next.prev = prev; x.next = null; }

        x.item = null;
        size--;
        return element;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        if (index == size) {
            add(element);
        } else {
            Node<E> succ = node(index);
            final Node<E> pred = succ.prev;
            final Node<E> newNode = new Node<>(pred, element, succ);
            succ.prev = newNode;

            if (pred == null) {
                head = newNode;
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(E[] c) {
        if (c == null || c.length == 0) return;
        for (E o : c) {
            add(o);
        }
    }

    @Override
    public void addAll(int index, E[] c) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        if (c == null || c.length == 0) return;

        Node<E> pred, succ;
        if (index == size) {
            succ = null;
            pred = tail;
        } else {
            succ = node(index);
            pred = succ.prev;
        }

        for (E o : c) {
            Node<E> newNode = new Node<>(pred, o, null);
            if (pred == null) {
                head = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }

        if (succ == null) {
            tail = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }
        size += c.length;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        return node(index).item;
    }

    @Override
    public void set(int index, E element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        Node<E> x = node(index);
        x.item = element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (Node<E> x = head; x != null; x = x.next) {

            if (o == null ? x.item == null : o.equals(x.item)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;

        for (Node<E> x = head; x != null; x = x.next) {
            result[i++] = x.item;
        }
        return result;
    }
}
