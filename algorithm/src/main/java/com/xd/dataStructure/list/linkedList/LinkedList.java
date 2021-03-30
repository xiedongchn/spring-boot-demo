package com.xd.dataStructure.list.linkedList;

import java.io.Serializable;

/**
 * 单链表
 *
 * @author xd
 * Created on 2021/3/29
 */
public class LinkedList<E> implements Serializable {
    private static final long serialVersionUID = 2460285019213128355L;

    private int size;

    private Node<E> first;

    public boolean isEmpty() {
        return size <= 0;
    }

    public void addFirst(E e) {
        Node<E> node = new Node<>(e);
        node.setNext(first);
        size++;
    }

    public void add(E item, int index) {
        if (illegalIndex(index)) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsString(index));
        }
        Node<E> current = getNode(index);
        Node<E> node = new Node<>(item);
        if (current == null) {
            first = node;
        } else {
            node.setNext(current.getNext());
            current.setNext(node);
        }
        size++;
    }

    public void removeFirst() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsString(0));
        }
        first = first.getNext();
        size--;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsString(index));
        }
        if (index == 0) {
            first = first.getNext();
        } else {
            Node<E> current = getNode(index - 1);
            Node<E> next = current.getNext();
            current.setNext(next.getNext());
        }
        size--;
    }

    public E get(int index) {
        if (illegalElement(index)) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsString(index));
        }
        Node<E> current = first;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getItem();
    }

    private Node<E> getNode(int index) {
        if (illegalElement(index)) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsString(index));
        }
        Node<E> current = first;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }

    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        private static final long serialVersionUID = 6877040723858203940L;

        private Node<E> currentNode = first;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            E e = currentNode.getItem();
            currentNode = currentNode.getNext();
            return e;
        }
    }

    public static class Node<E> {
        private E item;

        private Node<E> next;

        public Node(E item) {
            this.item = item;
        }

        public E getItem() {
            return item;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private boolean illegalIndex(int index) {
        return index < 0 || index > size;
    }

    private boolean illegalElement(int index) {
        return index < 0 || index >= size;
    }

    private boolean isLegalIndex(int index) {
        return !illegalIndex(index);
    }

    private String getIndexOutOfBoundsString(int index) {
        return "Index:" + index + ",Size:" + size;
    }

    E getFirst() {
        return first.getItem();
    }

    public int getSize() {
        return size;
    }
}
