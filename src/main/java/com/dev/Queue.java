package com.dev;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<T> implements Iterable<T> {
    private LinkedList<T> queue;

    public Queue() {
        this.queue = new LinkedList<>();
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public int length() {
        return this.queue.length();
    }

    public void enqueue(T value) {
        this.queue.prepend(value);
    }

    public T dequeue() {
        return this.queue.pop();
    }

    public T peek() {
        if (this.queue.isEmpty()) {
            return null;
        }
        return this.queue.tail.value;
    }


  public Iterator<T> iterator() {
    return new QueueIterator();
  }

  private class QueueIterator implements Iterator<T> {
    private Node<T> current = queue.head;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public T next() {
      if (!this.hasNext()) {
        throw new NoSuchElementException("no more elements in the queue");
      }

      T value = current.value;
      current = current.next;

      return value;
    }
  }
}
