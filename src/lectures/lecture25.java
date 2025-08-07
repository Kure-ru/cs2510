package lectures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayListIterator<T> implements Iterator<T> {
    ArrayList<T> items;
    int nextIdx;

    ArrayListIterator(ArrayList<T> items) {
        this.items = items;
        this.nextIdx = 0;
    }

    public boolean hasNext() {
        return this.nextIdx < this.items.size();
    }

    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("there are no nore items!");
        }
        T answer = this.items.get(this.nextIdx);
        this.nextIdx = this.nextIdx + 1;
        return answer;
    }

    public void remove() {
        throw new UnsupportedOperationException("Don't do this");
    }
}

interface IList25<T> extends Iterable<T> {
    boolean isCons();

    ConsList25<T> asCons();
}

class ConsList25<T> implements IList25<T> {
    T first;
    IList25<T> rest;

    ConsList25(T first, IList25<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    public boolean isCons() {
        return true;
    }

    public ConsList25<T> asCons() {
        return this;
    }

    public Iterator<T> iterator() {
        return new IList25Iterator<T>(this);
    }
}

class MtList25<T> implements IList25<T> {

    public boolean isCons() {
        return false;
    }

    public ConsList25<T> asCons() {
        throw new UnsupportedOperationException("Empty list has no Cons");
    }

    public Iterator<T> iterator() {
        return new IList25Iterator<T>(this);
    }
}


class IList25Iterator<T> implements Iterator<T> {
    IList25<T> items;

    IList25Iterator(IList25<T> items) {
        this.items = items;
    }

    public boolean hasNext() {
        return this.items.isCons();
    }

    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("there are no nore items!");
        }
        ConsList25<T> itemsAsCons = this.items.asCons();
        T answer = itemsAsCons.first;
        this.items = itemsAsCons.rest;
        return answer;
    }

    public void remove() {
        throw new UnsupportedOperationException("Don't do this");
    }
}

class Node<T> {
    T data;
    Node<T> next;
    Node<T> prev;
    boolean isSentinel;

    Node(T data, boolean isSentinel) {
        this.data = data;
        this.isSentinel = isSentinel;
    }
}

class DequeForwardIterator<T> implements Iterator<T> {
    Node<T> current;

    DequeForwardIterator(Node<T> start) {
        this.current = start;
    }

    public boolean hasNext() {
        return this.current != null && !this.current.isSentinel;
    }

    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No more elements!");
        }
        T data = this.current.data;
        this.current = this.current.next;
        return data;
    }
}

class DequeReverseIterator<T> implements Iterator<T> {
    Node<T> current;

    DequeReverseIterator(Node<T> start) {
        this.current = start;
    }

    public boolean hasNext() {
        return this.current != null && !this.current.isSentinel;
    }

    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No more elements!");
        }
        T data = this.current.data;
        this.current = this.current.prev;
        return data;
    }
}

class Deque<T> implements Iterable<T> {
    Node<T> sentinel;

    Deque(){
        this.sentinel = new Node<>(null, true);
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
    }

    public Iterator<T> iterator() {
        return new DequeForwardIterator<T>(this.sentinel.next);
    }

    Iterator<T> reverseIterator() {
        return new DequeReverseIterator<T>(this.sentinel.prev);
    }

    void addAtTail(T item) {
        Node<T> newNode = new Node<>(item, false);
        newNode.prev = this.sentinel.prev;
        newNode.next = this.sentinel;
        this.sentinel.prev.next = newNode;
        this.sentinel.prev = newNode;
    }

    T removeAtHead() {
        if (this.sentinel.next == this.sentinel) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node<T> first = this.sentinel.next;
        this.sentinel.next = first.next;
        first.next.prev = this.sentinel;
        return first.data;
    }

    int size() {
        int count = 0;
        Node<T> current = this.sentinel.next;
        while (current != this.sentinel) {
            count++;
            current = current.next;
        }
        return count;
    }

}

//for (T item : myDeque) {
//      iterates forward through myDeque
//        ...
//        }
//Iterator<T> revIter = myDeque.reverseIterator();
//while (revIter.hasNext()) {
//      iterates backward through myDeque
//T item = revIter.next();
//  ...
//          }

class FibonacciIterator implements Iterator<Integer> {
    int prevVal = 0;
    int curVal = 1;

    public boolean hasNext() {
        return true;
    }

    public Integer next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("there are no more items!");
        }
        int answer = this.prevVal + this.curVal;
        this.prevVal = this.curVal;
        this.curVal = answer;
        return answer;
    }

    public void remove() {
        throw new UnsupportedOperationException("Don't do this!");
    }
}

interface IBinaryTree<T> {
    boolean isNode();

    BTNode<T> asNode();
}

class BTNode<T> implements IBinaryTree<T> {
    T data;
    IBinaryTree<T> left;
    IBinaryTree<T> right;

    BTNode(T data, IBinaryTree<T> left, IBinaryTree<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public boolean isNode() {
        return true;
    }

    public BTNode<T> asNode() {
        return this;
    }
}

class BTLeaf<T> implements IBinaryTree<T> {
    public boolean isNode() {
        return false;
    }

    public BTNode<T> asNode() {
        throw new UnsupportedOperationException("Leaf cannot be cast to Node");
    }
}


class BreadthFirstIterator<T> implements Iterator<T> {
    Deque<IBinaryTree<T>> workList;

    BreadthFirstIterator(IBinaryTree<T> source) {
        this.workList = new Deque<IBinaryTree<T>>();
        this.addIfNotLeaf(source);
    }

    void addIfNotLeaf(IBinaryTree bt) {
        if (bt.isNode()) {
            this.workList.addAtTail(bt);
        }
    }

    public boolean hasNext() {
        return this.workList.size() > 0;
    }

    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("there are no more items!");
        }

        BTNode<T> node = this.workList.removeAtHead().asNode();
        this.addIfNotLeaf(node.left);
        this.addIfNotLeaf(node.right);

        return node.data;
    }

    public void remove() {
        throw new UnsupportedOperationException("Don't do this!");
    }
}