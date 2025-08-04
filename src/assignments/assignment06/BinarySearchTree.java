package assignments.assignment06;

import tester.Tester;

import java.util.Comparator;

interface IList<T> {
    boolean sameList(IList<T> other);
}

class Mt<T> implements IList<T> {
    public boolean sameList(IList<T> other) {
        return other instanceof Mt;
    }
}

class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;

    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    public boolean sameList(IList<T> other) {
        if (other instanceof Cons<?>) {
            Cons<T> otherCons = (Cons<T>) other;
            return this.first.equals(otherCons.first) && this.rest.sameList(otherCons.rest);
        }
        return false;
    }
}

class Append<T> extends Mt<T> {
    IList<T> left;
    IList<T> right;

    Append(IList<T> left, IList<T> right) {
        this.left = left;
        this.right = right;
    }

    public boolean sameList(IList<T> other) {
        IList<T> combined = new Append<>(right, new Mt<>());
        return left.sameList(combined) && combined.sameList(other);
    }
}

class Book6 {
    String title;
    String author;
    int price;

    Book6(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}

class BooksByTitle implements Comparator<Book6> {
    public int compare(Book6 b1, Book6 b2) {
        return b1.title.compareTo(b2.title);
    }
}

class BooksByAuthor implements Comparator<Book6> {
    public int compare(Book6 b1, Book6 b2) {
        return b1.author.compareTo(b2.author);
    }
}

class BooksByPrice implements Comparator<Book6> {
    public int compare(Book6 b1, Book6 b2) {
        return Integer.compare(b1.price, b2.price);
    }
}

abstract class ABST<T> {
    Comparator<T> order;

    ABST(Comparator<T> order) {
        this.order = order;
    }

    abstract boolean present(T item);

    abstract T getLeftmost();

    abstract ABST<T> getRight();

    abstract boolean sameTree(ABST<T> other);

    abstract boolean sameData(ABST<T> other);

    abstract IList<T> buildList();
}

class Leaf<T> extends ABST<T> {
    Leaf(Comparator<T> order) {
        super(order);
    }

    boolean present(T item) {
        return false;
    }

    T getLeftmost() {
        throw new RuntimeException("No leftmost item of an empty tree");
    }

    ABST<T> getRight() {
        throw new RuntimeException("No right of an empty tree");
    }

    boolean sameTree(ABST<T> other) {
        return other instanceof Leaf;
    }

    boolean sameData(ABST<T> other) {
        return other instanceof Leaf;
    }

    IList<T> buildList() {
        return new Mt<>();
    }
}

class Node<T> extends ABST<T> {
    T data;
    ABST<T> left;
    ABST<T> right;

    Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
        super(order);
        this.data = data;
        this.left = left;
        this.right = right;
    }

    boolean present(T item) {
        int cmp = order.compare(item, this.data);

        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return left.present(item);
        } else {
            return right.present(item);
        }
    }

    T getLeftmost() {
        if (left instanceof Leaf) {
            return data;
        } else {
            return left.getLeftmost();
        }
    }

    ABST<T> getRight() {
        if (right instanceof Leaf) {
            return right;
        } else {
            return new Node<T>(order, data, left.getRight(), right);
        }
    }

    boolean sameTree(ABST<T> other) {
        if (other instanceof Node<?>) {
            Node<T> otherNode = (Node<T>) other;
            return this.order.equals(otherNode.order) &&
                    this.data.equals(otherNode.data) &&
                    this.left.sameTree(otherNode.left) &&
                    this.right.sameTree(otherNode.right);
        }
        return false;
    }

    boolean sameData(ABST<T> other) {
        return this.buildList().sameList(other.buildList());
    }

    IList<T> buildList() {
        return new Append<>(left.buildList(), new Cons<>(data, right.buildList()));
    }
}

class BinaryTreeBooks {
    Book6 b1 = new Book6("Alpha", "Adams", 10);
    Book6 b2 = new Book6("Beta", "Baker", 15);
    Book6 b3 = new Book6("Gamma", "Clark", 20);
    Book6 b4 = new Book6("Delta", "Davis", 25);

    void testPresent(Tester t) {
        ABST<Book6> tree = new Node<>(new BooksByTitle(), b2, new Node<>(new BooksByTitle(), b1, new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())), new Node<>(new BooksByTitle(), b3, new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())));

        t.checkExpect(tree.present(b1), true);
        t.checkExpect(tree.present(b2), true);
        t.checkExpect(tree.present(b4), false);
    }

    void testGetLeftmost(Tester t) {
        ABST<Book6> tree = new Node<>(new BooksByTitle(), b2, new Node<>(new BooksByTitle(), b1, new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())), new Node<>(new BooksByTitle(), b3, new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())));

        t.checkExpect(tree.getLeftmost(), b1);
    }

    void testGetRight(Tester t) {
        ABST<Book6> tree = new Node<>(new BooksByTitle(), b2, new Node<>(new BooksByTitle(), b1, new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())), new Node<>(new BooksByTitle(), b3, new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())));

        ABST<Book6> rightSubtree = tree.getRight();
        t.checkExpect(rightSubtree.present(b3), true);
        t.checkExpect(rightSubtree.present(b4), false);
    }

    void testBuildList(Tester t) {
        ABST<Book6> tree = new Node<>(new BooksByTitle(), b2,
                new Node<>(new BooksByTitle(), b1,
                        new Leaf<>(new BooksByTitle()),
                        new Leaf<>(new BooksByTitle())),
                new Node<>(new BooksByTitle(), b3,
                        new Leaf<>(new BooksByTitle()),
                        new Leaf<>(new BooksByTitle())));

        IList<Book6> expectedList = new Cons<>(b1,
                new Cons<>(b2,
                        new Cons<>(b3,
                                new Mt<>())));

        t.checkExpect(tree.buildList().sameList(expectedList), true);
    }


}