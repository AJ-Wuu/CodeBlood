/*
 * @author AJWuu
 * Question: https://leetcode.com/discuss/interview-question/1593355/google-phone-interview-rejected
 */

import java.util.*;

public class CordTree {

    static class Node {
        private int length;

        public Node(int length) {
            this.length = length;
        }
    }

    static class LeafNode extends Node {
        private String value;

        public LeafNode(int length, String value) {
            super(length);
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    static class InternalNode extends Node {
        private Node leftChild;
        private Node rightChild;

        public InternalNode(int length, Node leftChild, Node rightChild) {
            super(length);
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }

    static class Tree {
        private Node root;

        public Tree(Node root) {
            this.root = root;
        }

        public Character findCordAtIndex(int index) {
            return findCordAtIndex(index, root);
        }

        public Character findCordAtIndex(int index, Node root) {
            if (index <= root.length) {
                if (root instanceof LeafNode) { // a leaf node, base case
                    String s = ((LeafNode) root).value;
                    return s.charAt(index - 1);
                } else { // an internal node
                    InternalNode internalNode = (InternalNode) root;
                    Node left = internalNode.leftChild;
                    Node right = internalNode.rightChild;
                    if (index <= left.length) { // go to left
                        return findCordAtIndex(index, left);
                    } else { // go to right
                        return findCordAtIndex(index - left.length, right);
                    }
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Node root = new InternalNode(26, new LeafNode(5, "ABCDE"), new InternalNode(21,
                new LeafNode(10, "FGHIJKLMNO"), new LeafNode(11, "PQRSTUVWXYZ")));
        Tree cordTree = new Tree(root);

        System.out.println(cordTree.findCordAtIndex(2));
        System.out.println(cordTree.findCordAtIndex(8));
        System.out.println(cordTree.findCordAtIndex(16));
    }

}
