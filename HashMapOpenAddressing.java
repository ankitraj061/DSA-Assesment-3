import java.util.ArrayList;

class Entry<K, V> {
    K key;
    V value;
    boolean isDeleted;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.isDeleted = false;
    }
}

public class HashMapOpenAddressing<K, V> {
    private int size;
    private int capacity;
    private ArrayList<Entry<K, V>> data;

    public HashMapOpenAddressing() {
        this.size = 0;
        this.capacity = 10;
        this.data = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            data.add(null);
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    private void resize() {
        capacity *= 2;
        ArrayList<Entry<K, V>> oldData = data;
        data = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            data.add(null);
        }
        size = 0;

        for (Entry<K, V> entry : oldData) {
            if (entry != null && !entry.isDeleted) {
                insert(entry.key, entry.value);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void insert(K key, V value) {
        if (size >= capacity / 2) {
            resize();
        }

        int index = hash(key);
        int initialIndex = index;

        while (data.get(index) != null && !data.get(index).key.equals(key)) {
            index = (index + 1) % capacity;
            if (index == initialIndex) {
                resize();
                index = hash(key);
                initialIndex = index;
            }
        }

        if (data.get(index) == null || data.get(index).isDeleted) {
            data.set(index, new Entry<>(key, value));
            size++;
        } else {
            data.get(index).value = value;
        }
    }

    public V get(K key) {
        int index = hash(key);
        int initialIndex = index;

        while (data.get(index) != null) {
            if (!data.get(index).isDeleted && data.get(index).key.equals(key)) {
                return data.get(index).value;
            }
            index = (index + 1) % capacity;
            if (index == initialIndex) {
                break;
            }
        }

        return null;
    }

    public void remove(K key) {
        int index = hash(key);
        int initialIndex = index;

        while (data.get(index) != null) {
            if (!data.get(index).isDeleted && data.get(index).key.equals(key)) {
                data.get(index).isDeleted = true;
                size--;
                return;
            }
            index = (index + 1) % capacity;
            if (index == initialIndex) {
                break;
            }
        }
    }

    public void display() {
        for (int i = 0; i < capacity; i++) {
            if (data.get(i) != null && !data.get(i).isDeleted) {
                System.out.println("Index " + i + ": " + data.get(i).key + " " + data.get(i).value);
            }
        }
    }

    public static void main(String[] args) {
        HashMap<Object, Integer> map = new HashMap<>();
        map.insert("kfshdgkdf", 1);
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
        System.out.println("Value for key 2: " + value);
        System.out.println();

        map.remove("raj");

        System.out.println("Size of the map: " + map.getSize());

        map.display();
    }
}
