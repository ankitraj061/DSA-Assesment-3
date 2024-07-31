import java.util.ArrayList;

class Entry<K, V> {
    K key;
    V value;
    int hash;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.hash = key.hashCode();
    }
}

public class HashMap<K, V> {
    int size;
    int capacity;
    ArrayList<ArrayList<Entry<K, V>>> data;

    public HashMap() {
        this.size = 0;
        this.capacity = 10;
        data = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            data.add(new ArrayList<>());
        }
    }

    public int getSize() {
        return size;
    }

    public void insert(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value);
        int index = Math.abs(entry.hash % capacity);
        boolean isUpdated = false;
        for (Entry<K, V> e : data.get(index)) {
            if (e.key.equals(key)) {
                e.value = value;
                isUpdated = true;
                return;
            }
        }
        if (!isUpdated) {
            data.get(index).add(entry);
            size++;
        }
    }

    public V get(K key) {
        int index = Math.abs(key.hashCode() % capacity);
        for (Entry<K, V> e : data.get(index)) {
            if (e.key.equals(key)) {
                return e.value;
            }
        }
        return null;
    }

    public void remove(K key) {
        int index = Math.abs(key.hashCode() % capacity);
        for (Entry<K, V> e : data.get(index)) {
            if (e.key.equals(key)) {
                data.get(index).remove(e);
                size--;
                return;
            }
        }
    }

    public void display() {
        for (ArrayList<Entry<K, V>> list : data) {
            for (Entry<K, V> e : list) {
                System.out.println(e.key + " " + e.value);
            }
        }
    }

    static public void main(String[] args) {
        HashMap<Object, Integer> map = new HashMap<>();
        map.insert("1", 1);
        map.insert(2, 7);
        map.insert("3", 3);
        map.insert("1", 0);
        map.insert("ankit", 0);
        map.insert("raj", 0);
        map.insert("raj", 1);
        map.insert("rj", 0);
        map.insert("j", 0);

        map.display();
        Integer value = map.get(2);
        System.out.println(value);
        System.out.println();

        map.remove("raj");

        System.out.println(map.getSize());

        map.display();
    }
}
