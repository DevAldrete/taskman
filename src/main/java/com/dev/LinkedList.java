package com.dev;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<T> implements Iterable<T> {
    Node<T> head;
    Node<T> tail;
    private int length;

    public LinkedList() {
        this.length = 0;
        this.head = null;
        this.tail = null;
    }

    public int length() {
        return this.length;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public void prepend(T value) {
        Node<T> newNode = new Node<T>(value);
        if (this.isEmpty()) {
            this.head = newNode;
            this.tail = this.head;
            this.length++;
            return;
        }

        this.head.prev = newNode;
        newNode.next = this.head;
        this.head = newNode;
        this.length++;
    }

    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (this.isEmpty()) {
            this.head = newNode;
            this.tail = this.head;
            this.length++;
            return;
        }

        newNode.prev = this.tail;
        this.tail.next = newNode;
        this.tail = newNode;
        this.length++;
    }

    public T pop() {
        if (this.isEmpty()) {
            return null;
        }

        Node<T> poppedNode = this.tail;
        this.tail = this.tail.prev;
        this.length--;

        if (this.tail == null) {
            this.head = null;
        } else {
            this.tail.next = null;
        }

        poppedNode.prev = null;
        poppedNode.next = null;

        return poppedNode.value;
    }

    public boolean insert(T value, int idx) {
        if (this.isEmpty()) {
            this.add(value);
            return true;
        }

        if (idx > this.length) {
            return false;
        }

        Node<T> curr = this.head;

        for (int i = idx; i > 0; i--) {
            curr = curr.next;
        }

        Node<T> newNode = new Node<>(value);

        newNode.next = curr.next;
        newNode.prev = curr;
        curr.next = newNode;

        this.length++;

        return true;
    }

    public boolean delete(T value) {
        Node<T> curr = this.head;

        while (curr != null) {
            if (Objects.equals(curr.value, value)) {
                this.length--;

                // Actualizamos head o tail segun corresponda, cubriendo tambien el caso
                // donde el nodo a remover es el unico en la lista (head == tail)
                if (curr.prev != null) {
                    curr.prev.next = curr.next;
                } else {
                    this.head = curr.next;
                }

                if (curr.next != null) {
                    curr.next.prev = curr.prev;
                } else {
                    this.tail = curr.prev;
                }

                curr.prev = null;
                curr.next = null;
                return true;
            }
            curr = curr.next;
        }

        return false;
    }

    public T find(T value) {
        Node<T> curr = this.head;

        while (curr != null) {
            if (Objects.equals(curr.value, value)) {
                return curr.value;
            }
            curr = curr.next;
        }

        return null;
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("no more elements in the list");
            }

            T val = current.value;
            current = current.next;
            return val;
        }
    }
}
