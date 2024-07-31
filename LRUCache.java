import java.util.HashMap;

class DLL {
    Node head;
    Node tail;

    class Node {
        int val;
        Node next;
        Node prev;
        int key;

        Node(int key, int val) {
            this.val = val;
            this.next = null;
            this.prev = null;
            this.key = key;
        }
    }

    DLL() {
        this.head = null;
        this.tail = null;
    }

    public void append(Node node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    public void moveToEnd(Node node) {
        if (node == null || head == null || node == tail) {
            return;
        }

        if (node == head) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
        } else {
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            }
        }

        node.next = null;
        node.prev = tail;
        if (tail != null) {
            tail.next = node;
        }
        tail = node;

        if (head == null) {
            head = node;
        }
    }

    public void delete() {
        if (head == null) {
            return;
        }

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
    }
}

class LRUCache {
    DLL dll;
    HashMap<Integer, DLL.Node> map;
    int cap;

    public LRUCache(int capacity) {
        dll = new DLL();
        map = new HashMap<>();
        this.cap = capacity;
    }

    public int get(int key) {
        DLL.Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        dll.moveToEnd(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            DLL.Node node = map.get(key);
            node.val = value;
            dll.moveToEnd(node);
        } else {
            if (map.size() >= cap) {
                DLL.Node nodeToRemove = dll.head;
                dll.delete();
                map.remove(nodeToRemove.key);
            }
            DLL.Node node = dll.new Node(key, value);
            dll.append(node);
            map.put(key, node);
        }
    }
}
