package com.xd.dataStructure.tree.binaryTree;

import java.io.Serializable;

/**
 * 二叉树
 *
 * @author xd
 * Created on 2021/3/29
 */
public class BinaryTree<E> implements Serializable {
    private static final long serialVersionUID = 2941992466580460318L;

    private Node<E> root;

    private int size;

    private int level;

    public static class Node<E> {
        private E item;

        private int level;

        private Node<E> parent;

        private Node<E> left;

        private Node<E> right;

        public Node(E item) {
            this.item = item;
        }

        public E getItem() {
            return item;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }
    }

    public Node<E> getRoot() {
        return root;
    }

    public int getLevel() {
        return level;
    }

    public int getSize() {
        return size;
    }
}
