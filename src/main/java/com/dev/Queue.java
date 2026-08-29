package com.dev;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Cola (FIFO) generica basada en LinkedList: los elementos se atendienen
 * en orden de llegada (el primero en entrar es el primero en salir).
 */
public class Queue<T> implements Iterable<T> {
    private LinkedList<T> queue;

    public Queue() {
        this.queue = new LinkedList<>();
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    /** Numero de elementos en la cola. */
    public int length() {
        return this.queue.length();
    }

    /** Agrega un elemento al frente de la cola (primero en ser atendido). */
    public void enqueue(T value) {
        this.queue.prepend(value);
    }

    /** Remueve y devuelve el elemento mas antiguo de la cola. */
    public T dequeue() {
        return this.queue.pop();
    }

    /** Devuelve (sin remover) el elemento mas antiguo, o null si esta vacia. */
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
