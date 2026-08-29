package com.dev;

/**
 * Nodo de una lista doblemente enlazada. Guarda un valor y las
 * referencias al nodo anterior (prev) y siguiente (next).
 */
public class Node<T> {
    T value;
    Node<T> next;
    Node<T> prev;

    /**
     * Crea un nodo aislado (sin next ni prev) con el valor dado.
     */
    public Node(T value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}
