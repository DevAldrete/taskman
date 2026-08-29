package com.dev;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Pila (LIFO) generica basada en LinkedList: el ultimo elemento en entrar
 * es el primero en salir.
 */
public class Stack<T> implements Iterable<T> {
    private LinkedList<T> stack;

    public Stack() {
        this.stack = new LinkedList<>();
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    /** Numero de elementos en la pila. */
    public int length() {
        return this.stack.length();
    }

    /** Agrega un elemento a la cima de la pila (se atendera primero). */
    public void push(T value) {
        this.stack.add(value);
    }

    /** Remueve y devuelve el elemento de la cima (el mas reciente). */
    public T pop() {
        return this.stack.pop();
    }

    /** Devuelve (sin remover) el elemento de la cima, o null si esta vacia. */
    public T peek() {
        if (this.stack.isEmpty()) {
            return null;
        }
        return this.stack.tail.value;
    }

    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T> {
        private Node<T> current = stack.head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("no more elements in the stack");
            }
            T value = current.value;
            current = current.next;

            return value;
        }
    }
}
