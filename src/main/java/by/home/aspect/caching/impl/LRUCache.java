package by.home.aspect.caching.impl;

import by.home.aspect.caching.Cache;

import java.util.HashMap;

public class LRUCache<E> implements Cache<E> {

    private final int capacity;
    private HashMap<Integer, Node> hs = new HashMap<>();
    private Node head = new Node(-1, null);
    private Node tail = new Node(-1, null);

    public LRUCache(int capacity) {
        this.capacity = capacity;
        tail.prev = head;
        head.next = tail;
    }

    public E get(int key) {
        if (!hs.containsKey(key)) {
            return null;
        }

        return hs.get(key).value;
    }

    public void delete(int key) {
        Node current = hs.get(key);
        hs.remove(hs.get(key).key);
        head.next = head.next.next;
        head.next.prev = head;
        moveToTail(current);
    }

    public void set(int key, E value) {
        if (get(key) != null) {
            hs.get(key).value = value;
            return;
        }

        if (hs.size() == capacity) {
            hs.remove(head.next.key);
            head.next = head.next.next;
            head.next.prev = head;
        }

        Node insert = new Node(key, value);
        hs.put(key, insert);
        moveToTail(insert);
    }

    public int size() {
        return hs.size();
    }

    private void moveToTail(Node current) {
        current.prev = tail.prev;
        tail.prev = current;
        current.prev.next = current;
        current.next = tail;
    }

    private class Node {
        Node prev;
        Node next;
        int key;
        E value;

        public Node(int key, E value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

}