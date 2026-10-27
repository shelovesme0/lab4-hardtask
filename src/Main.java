import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //MyArrayList
        System.out.println("=== 1. Демонстрація MyArrayList ===");
        MyList<String> arrayList = new MyArrayList<>();
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");
        System.out.println("Початковий список: " + Arrays.toString(arrayList.toArray()));

        arrayList.add(1, "Orange");
        System.out.println("Після вставки Orange на індекс 1: " + Arrays.toString(arrayList.toArray()));

        arrayList.remove(2);
        System.out.println("Після видалення елемента за індексом 2: " + Arrays.toString(arrayList.toArray()));
        String first = arrayList.get(0);
        System.out.println("Елемент під індексом 0: " + first);
        System.out.println("Розмір MyArrayList: " + arrayList.size());

        // MyLinkedList
        System.out.println("\n=== 2. Демонстрація MyLinkedList ===");
        MyList<String> linkedList = new MyLinkedList<>();
        linkedList.add("One");
        linkedList.add("Two");

        String[] moreItems = {"Three", "Four"};
        linkedList.addAll(moreItems);
        System.out.println("Після додавання масиву: " + Arrays.toString(linkedList.toArray()));

        linkedList.set(0, "Zero"); // Зміна елемента
        System.out.println("Після заміни першого елемента: " + Arrays.toString(linkedList.toArray()));
        System.out.println("Індекс елемента 'Three': " + linkedList.indexOf("Three"));


        System.out.println("\n=== 3. Демонстрація MyLinkedHashSet ===");
        MyLinkedHashSet<String> set = new MyLinkedHashSet<>();
        set.add("First");
        set.add("Second");
        set.add("Third");
        set.add("Second"); // Спроба додати дублікат

        System.out.println("Вміст Set (дублікат проігноровано, порядок збережено): " + Arrays.toString(set.toArray()));
        System.out.println("Чи містить 'First'? " + set.contains("First"));
        System.out.println("Розмір Set: " + set.size());


        System.out.println("\n=== 4. Демонстрація MyCache (LRU + Expiry) ===");
        // Створюємо кеш на 3 елементи з часом життя 1 секунда (1000 мс)
        MyCache<String, String> cache = new MyCache<>(3, 1000);

        // 4.1. LRU (Витіснення)
        cache.put("1", "A");
        cache.put("2", "B");
        cache.put("3", "C");
        System.out.println("Додано 3 елементи. Розмір кешу: " + cache.size());

        // Звертаємось до елемента "1", щоб він став "найновішим"
        cache.get("1");

        // Додаємо 4-й елемент. Оскільки ліміт 3, найстаріший (це "2", бо "1" ми щойно читали) має видалитися
        cache.put("4", "D");
        System.out.println("Додано 4-й елемент. Розмір кешу: " + cache.size());
        System.out.println("Ключ '1' (був прочитаний) -> " + cache.get("1"));
        System.out.println("Ключ '2' (мав бути витіснений LRU) -> " + cache.get("2"));

        // 4.2. Expiry
        System.out.println("\nОчікуємо 1.5 секунди для перевірки експірації...");
        Thread.sleep(1500);

        System.out.println("Ключ '3' після закінчення часу життя -> " + cache.get("3"));


        System.out.println("\n=== 5. Демонстрація wildcard (? extends E) ===");
        MyList<Integer> ints = new MyArrayList<>();
        ints.add(1);
        ints.add(2);

        MyList<Double> doubles = new MyLinkedList<>();
        doubles.add(3.5);

        // addAll(MyList<? extends Number>) приймає і MyList<Integer>, і MyList<Double>
        MyList<Number> numbers = new MyArrayList<>();
        numbers.addAll(ints);
        numbers.addAll(doubles);
        printList("MyList<Number> після addAll(ints) і addAll(doubles): ", numbers);

        // Те саме для MyLinkedHashSet: ints додаємо двічі, дублікати не потрапляють
        MyLinkedHashSet<Number> numberSet = new MyLinkedHashSet<>();
        numberSet.addAll(ints);
        numberSet.addAll(ints);
        numberSet.addAll(doubles);
        System.out.println("MyLinkedHashSet<Number> після addAll: " + Arrays.toString(numberSet.toArray()));

        // printList(MyList<?>) приймає список будь-якого типу
        printList("MyList<String>: ", arrayList);
        printList("MyList<Integer>: ", ints);
    }

    static void printList(String label, MyList<?> list) {
        System.out.println(label + Arrays.toString(list.toArray()));
    }
}
