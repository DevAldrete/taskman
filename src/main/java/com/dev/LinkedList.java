package com.dev;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Lista doblemente enlazada genérica. Mantiene punteros a la cabeza (head)
 * y la cola (tail) para operar en O(1) en ambos extremos, y soporta
 * iteración con for-each (interfaz Iterable).
 */
public class LinkedList<T> implements Iterable<T> {
    Node<T> head;
    Node<T> tail;
    private int length;

    public LinkedList() {
        this.length = 0;
        this.head = null;
        this.tail = null;
    }

    /** Numero de elementos en la lista. */
    public int length() {
        return this.length;
    }

    /** Indica si la lista no tiene elementos. */
    public boolean isEmpty() {
        return this.length == 0;
    }

    /** Agrega un elemento al inicio de la lista (nueva cabeza). */
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

    /** Agrega un elemento al final de la lista (nueva cola). */
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

    /**
     * Remueve y devuelve el ultimo elemento (la cola). Devuelve null si
     * la lista esta vacia. Al vaciarse actualiza head y limpia las
     * referencias del nodo removido.
     */
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

    /**
     * Inserta un elemento en la posicion idx (despues del nodo en esa
     * posicion). Devuelve false si idx excede la longitud de la lista.
     */
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

    /** Elimina el primer nodo cuyo valor sea igual al dado. Devuelve true si se encontro y elimino. */
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

    /** Busca el primer nodo con el valor dado y lo devuelve, o null si no existe. */
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
