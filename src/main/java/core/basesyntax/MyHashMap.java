package core.basesyntax;

import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> {

    private final static int  INITIAL_CAPACITY = 16;
    private final static double DEFAULT_LOAD_FACTOR = 0.75;
    private final static int SIZE_MULTIPLIER = 2;
    private int current_capacity = 16;
    private Node<K, V>[] table;

    private int size;

    public MyHashMap() {
        table = new Node[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public void put(K key, V value) {
        resize();
        int index = computeIndex(key);
        Node<K, V> newNode = new Node<>(key, value, null);
        if(table[index] == null) {
            table[index] = newNode;
            table[index].value = value;
            size++;
        } else {
            Node<K, V> currentNode = table[index];
            while (currentNode != null) {
                if (Objects.equals(key, currentNode.key)) {
                    currentNode.value = value;
                    return;
                } else if(currentNode.next == null) {
                    currentNode.next= newNode;
                    break;
                }
                currentNode = currentNode.next;
            }
            size++;
        }
    }

    private void resize() {
        if(size >= current_capacity * DEFAULT_LOAD_FACTOR) {
            current_capacity = current_capacity * SIZE_MULTIPLIER;
            Node<K, V>[] oldTable = table;
            table = new Node[current_capacity];
            size=0;
            for(Node<K, V> node: oldTable) {
                while (node != null) {
                    put(node.key, node.value);
                    node = node.next;
                }
            }
        }
    }

    private int computeIndex(K key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % current_capacity;
    }



    @Override
    public V getValue(K key) {
        int index = computeIndex(key);
        Node<K, V> currentNode = table[index];
        while (currentNode != null) {
            if(Objects.equals(key, currentNode.key)) {
                return  currentNode.value;
            }
            currentNode = currentNode.next;
        }
        return  null;
    }

    @Override
    public int getSize() {
        return size;
    }

    private static class Node<K, V> {
        private  K key;
        private  V value;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V>next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

}
