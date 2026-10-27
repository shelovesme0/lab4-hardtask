public class MyLinkedHashSet<E> {
    private static final int INITIAL_CAPACITY = 16;
    private Node<E>[] table;
    private int size;

    private Node<E> head;
    private Node<E> tail;

    static class Node<E> {
        E key;
        int hash;
        Node<E> next;
        Node<E> before;
        Node<E> after;

        Node(int hash, E key, Node<E> next) {
            this.hash = hash;
            this.key = key;
            this.next = next;
        }
    }

    public MyLinkedHashSet() {
        table = new Node[INITIAL_CAPACITY];
    }

    private int hash(Object key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % table.length);
    }

    public boolean add(E e) {
        int hash = hash(e);
        Node<E> node = table[hash];

        while (node != null) {
            if (node.hash == hash && (e == null ? node.key == null : e.equals(node.key))) {
                return false;
            }
            node = node.next;
        }

        Node<E> newNode = new Node<>(hash, e, table[hash]);
        table[hash] = newNode;

        if (tail == null) {
            head = newNode;
        } else {
            newNode.before = tail;
            tail.after = newNode;
        }
        tail = newNode;

        size++;
        return true;
    }

    public void addAll(MyList<? extends E> c) {
        for (int i = 0; i < c.size(); i++) {
            add(c.get(i));
        }
    }

    public boolean contains(E e) {
        int hash = hash(e);
        Node<E> node = table[hash];
        while (node != null) {
            if (node.hash == hash && (e == null ? node.key == null : e.equals(node.key))) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;

        for (Node<E> e = head; e != null; e = e.after) {
            result[i++] = e.key;
        }
        return result;
    }
}