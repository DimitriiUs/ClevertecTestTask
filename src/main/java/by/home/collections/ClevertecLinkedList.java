package by.home.collections;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ClevertecLinkedList<E> implements List<E> {

    /**
     * The size of the list
     */
    private AtomicInteger size = new AtomicInteger(0);

    /**
     * First node of the list
     */
    private volatile Node<E> first;

    /**
     * Last node of the list
     */
    private volatile Node<E> last;

    /**
     * The lock protecting all mutators
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Constructs an empty list
     */
    public ClevertecLinkedList() {
    }

    /**
     * Constructs a list with elements of specified collection
     *
     * @param collection - collection with elements
     * @throws NullPointerException - if specified collection is null
     */
    public ClevertecLinkedList(Collection<? extends E> collection) {
        this();
        addAll(collection);
    }

    /**
     * Returns size of the list
     *
     * @return size of the list
     */
    @Override
    public int size() {
        return size.get();
    }

    /**
     * Returns {@code true} if list is empty
     *
     * @return {@code true} if list is empty
     */
    @Override
    public boolean isEmpty() {
        return size.get() == 0;
    }

    @Override
    public boolean contains(Object o) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            return indexOf(o) >= 0;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the index of first entry of element in this list or -1 if
     * list doesn't contain element
     *
     * @param o element
     * @return the index of first entry of element in this list or -1 if
     * list doesn't contain element
     */
    @Override
    public int indexOf(Object o) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            int index = 0;
            if (o == null) {
                for (Node<E> x = first; x != null; x = x.next) {
                    if (x.element == null)
                        return index;
                    index++;
                }
            } else {
                for (Node<E> x = first; x != null; x = x.next) {
                    if (o.equals(x.element))
                        return index;
                    index++;
                }
            }
            return -1;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the index of last entry of element in this list or -1 if
     * list doesn't contain element
     *
     * @param o element
     * @return the last of first entry of element in this list or -1 if
     * list doesn't contain element
     */
    @Override
    public int lastIndexOf(Object o) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            int index = size.get();
            if (o == null) {
                for (Node<E> x = last; x != null; x = x.previous) {
                    index--;
                    if (x.element == null)
                        return index;
                }
            } else {
                for (Node<E> x = last; x != null; x = x.previous) {
                    index--;
                    if (o.equals(x.element))
                        return index;
                }
            }
            return -1;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns Object's array of elements of this list
     *
     * @return Object's array of elements of this list
     */
    @Override
    public Object[] toArray() {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            Object[] result = new Object[size.get()];
            int i = 0;

            for (Node<E> x = first; x != null; x = x.next) {
                result[i++] = x.element;
            }
            return result;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns an array with type equal to received array a
     *
     * @param a an array with a type for the returning array
     * @return an array with type equal to received array a
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            a = (T[]) Array.newInstance(
                    a.getClass().getComponentType(), size.get());
            int i = 0;
            Object[] result = a;

            for (Node<E> x = this.first; x != null; x = x.next) {
                result[i++] = x.element;
            }

            return a;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Add the specified element to the end of this list
     *
     * @param e - element
     * @return {@code true}
     */
    @Override
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            linkLast(e);
        } finally {
            lock.unlock();
        }

        return true;
    }

    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;

        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size.incrementAndGet();
    }

    /**
     * Removes the first entry of the specified element from this list
     *
     * @param o to be removed from this list
     * @return {@code true} if this list contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            if (o == null) {
                for (Node<E> x = first; x != null; x = x.next) {
                    if (x.element == null) {
                        unlink(x);
                        return true;
                    }
                }
            } else {
                for (Node<E> x = first; x != null; x = x.next) {
                    if (o.equals(x.element)) {
                        unlink(x);
                        return true;
                    }
                }
            }

            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Unlinks node x
     *
     * @param x - node for unlinking
     * @return unlinked element
     */
    private E unlink(Node<E> x) {
        final E element = x.element;
        final Node<E> next = x.next;
        final Node<E> previous = x.previous;

        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
            x.previous = null;
        }

        if (next == null) {
            last = previous;
        } else {
            next.previous = previous;
            x.next = null;
        }

        x.element = null;
        size.decrementAndGet();

        return element;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Add all elements in the specified collection to the end of this list
     *
     * @param c - collection
     * @return {@code true} if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size.get(), c);
    }

    /**
     * Add all the elements in the specified collection into this
     * list, starting at the specified position
     *
     * @param index for specified position
     * @param c     - collection
     * @return {@code true} if this list changed as a result of the call
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException      if the specified collection is null
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            checkPositionIndex(index);

            Object[] a = c.toArray();
            int numNew = a.length;
            if (numNew == 0)
                return false;

            Node<E> pred, succ;
            if (index == size.get()) {
                succ = null;
                pred = last;
            } else {
                succ = node(index);
                pred = succ.previous;
            }

            for (Object o : a) {
                @SuppressWarnings("unchecked") E e = (E) o;
                Node<E> newNode = new Node<>(pred, e, null);
                if (pred == null)
                    first = newNode;
                else
                    pred.next = newNode;
                pred = newNode;
            }

            if (succ == null) {
                last = pred;
            } else {
                pred.next = succ;
                succ.previous = pred;
            }

            size.addAndGet(numNew);
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the Node at the specified element index
     *
     * @return the Node at the specified element index
     */
    private Node<E> node(int index) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            Node<E> x;

            if (index < (size.get() >> 1)) {
                x = first;
                for (int i = 0; i < index; i++)
                    x = x.next;
            } else {
                x = last;
                for (int i = size.get() - 1; i > index; i--)
                    x = x.previous;
            }

            return x;
        } finally {
            lock.unlock();
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size.get()) {
            throw new IndexOutOfBoundsException(getMessageForIndexOutOfBoundException(index));
        }
    }

    private String getMessageForIndexOutOfBoundException(int index) {
        return "Received index:"
                + index
                + " "
                + "list size:"
                + size;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes all the elements from this list
     */
    @Override
    public void clear() {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            Node<E> next;
            for (Node<E> x = first; x != null; x = next) {
                next = x.next;
                x.element = null;
                x.next = null;
                x.previous = null;
            }

            first = this.last = null;
            size.set(0);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gets element at specified position
     *
     * @param index - position
     * @return element at specified position
     */
    @Override
    public E get(int index) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            checkElementIndex(index);

            return node(index).element;
        } finally {
            lock.unlock();
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size.get()) {
            throw new IndexOutOfBoundsException(
                    getMessageForIndexOutOfBoundException(index));
        }
    }

    /**
     * Set element at specified position
     *
     * @param index   - position
     * @param element - element for replacing
     * @return replaced element
     */
    @Override
    public E set(int index, E element) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            checkElementIndex(index);

            Node<E> x = node(index);
            E oldVal = x.element;
            x.element = element;

            return oldVal;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Adds specified element at specified position
     *
     * @param index   - specified position
     * @param element - specified element
     * @throws IndexOutOfBoundsException -
     */
    @Override
    public void add(int index, E element) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            checkPositionIndex(index);

            if (index == size.get()) {
                linkLast(element);
            } else {
                linkBefore(element, node(index));
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Inserts element before node
     *
     * @param element - element for inserting
     * @param node    - inserting before node
     */
    private void linkBefore(E element, Node<E> node) {
        final Node<E> pred = node.previous;
        final Node<E> newNode = new Node<>(pred, element, node);
        node.previous = newNode;

        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;

        size.incrementAndGet();
    }

    /**
     * Removes element at specified position
     *
     * @param index - specified position
     * @return removed element
     */
    @Override
    public E remove(int index) {
        final ReentrantLock lock = this.lock;

        try {
            lock.lock();
            checkElementIndex(index);

            return unlink(node(index));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> previous;

        public Node(Node<E> previous, E element, Node<E> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
}
