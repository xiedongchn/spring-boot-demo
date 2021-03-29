package com.xd.dataStructure.list.doubleLinkedList;

import java.io.Serializable;

/**
 * 双向链表
 *
 * @author xd
 * Created on 2021/3/29
 */
public class DoubleLinkedList<E> implements Serializable {
    private static final long serialVersionUID = -2638970050205770595L;

    private int size;

    private Node<E> first;

    private Node<E> last;

    public void addFirst(E e) {
        Node<E> node = new Node<>(e);
        node.setNext(first);
        if (first != null) {
            first.prev = node;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> node = new Node<>(e);
        if (last == null) {
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
        }
        size++;
    }

    public void add(E item, int index) {
        if (illegalIndex(index)) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsString(index));
        }
        Node<E> current = getNode(index);
        if (current == null) {
            first = new Node<>(item);
        } else {
            Node<E> node = new Node<>(item);
            node.setNext(current.getNext());
            current.setNext(node);
            node.prev = current;
        }
        size++;
    }

    public void removeFirst() {
        if (size <= 0) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsString(0));
        }
        first = first.getNext();
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        size--;
    }

    public void removeLast() {
        if (size <= 0) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsString(0));
        }
        last = last.getPrev();
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        size--;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsString(index));
        }
        if (index == 0) {
            first = first.getNext();
            if (first != null) {
                first.prev = null;
            } else {
                last = null;
            }
        } else {
            Node<E> current = getNode(index);
            current.getPrev().setNext(current.getNext());
            if (current.getNext() != null) {
                current.getNext().setPrev(current.getPrev());
            }
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

        private Node<E> prev;

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

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
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

    public Node<E> getLast() {
        return last;
    }

    public int getSize() {
        return size;
    }
}
