import java.util.HashMap;
import java.util.Map;

class LFUCache {
    private final int capacity;
    private int size;
    private int minFrequency;
    private final Map<Integer, Node> keyToNode;
    private final Map<Integer, DoublyLinkedList> frequencyToLinkedList;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.minFrequency = 0;
        this.keyToNode = new HashMap<>();
        this.frequencyToLinkedList = new HashMap<>();
    }

    public int get(int key) {
        if (!keyToNode.containsKey(key)) {
            return -1;
        }

        Node node = keyToNode.get(key);
        updateFrequency(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }

        if (keyToNode.containsKey(key)) {
            Node node = keyToNode.get(key);
            node.value = value;
            updateFrequency(node);
        } else {
            if (size == capacity) {
                evictNode();
            }

            size++;
            minFrequency = 1;
            Node newNode = new Node(key, value);
            keyToNode.put(key, newNode);
            frequencyToLinkedList.computeIfAbsent(1, k -> new DoublyLinkedList()).addNode(newNode);
        }
    }

    private void updateFrequency(Node node) {
        int frequency = node.frequency;
        DoublyLinkedList list = frequencyToLinkedList.get(frequency);
        list.removeNode(node);

        if (frequency == minFrequency && list.isEmpty()) {
            minFrequency++;
        }

        node.frequency++;
        frequencyToLinkedList.computeIfAbsent(node.frequency, k -> new DoublyLinkedList()).addNode(node);
    }

    private void evictNode() {
        DoublyLinkedList list = frequencyToLinkedList.get(minFrequency);
        Node nodeToRemove = list.removeFirst();
        keyToNode.remove(nodeToRemove.key);
        size--;
    }

    private static class Node 
	{
        int key;
        int value;
        int frequency;
        Node next;
        Node prev;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    private static class DoublyLinkedList {
        Node head;
        Node tail;

        DoublyLinkedList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        void addNode(Node node) {
            Node nextNode = head.next;
            head.next = node;
            node.prev = head;
            node.next = nextNode;
            nextNode.prev = node;
        }

        Node removeFirst() {
            if (isEmpty()) {
                return null;
            }

            Node firstNode = head.next;
            removeNode(firstNode);
            return firstNode;
        }

        void removeNode(Node node) {
            Node prevNode = node.prev;
            Node nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }

        boolean isEmpty() {
            return head.next == tail;
        }
    }
}
