package com.dev;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<T> implements Iterable<T> {
    private LinkedList<T> stack;

    public Stack() {
        this.stack = new LinkedList<>();
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public int length() {
        return this.stack.length();
    }

    public void push(T value) {
        this.stack.add(value);
    }

    public T pop() {
        return this.stack.pop();
    }

    public T peek() {
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
