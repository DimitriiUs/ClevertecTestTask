package by.home.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class ClevertecArrayList<E> implements List<E> {

    /**
     * The array in which all elements of the list are stored
     */
    private volatile Object[] elements;

    /**
     * The lock protecting all mutators
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Gets the elements
     *
     * @return elements
     */
    private Object[] getElements() {
        return elements;
    }

    /**
     * Sets the elements
     *
     * @param a - array with elements
     */
    private void setElements(Object[] a) {
        elements = a;
    }

    /**
     * Constructs an empty list
     */
    public ClevertecArrayList() {
        setElements(new Object[0]);
    }

    /**
     * Constructs a list with elements of specified collection
     *
     * @param collection - collection with elements
     * @throws NullPointerException - if specified collection is null
     */
    public ClevertecArrayList(Collection<? extends E> collection) {
        Object[] a;

        if (collection.getClass() == ClevertecArrayList.class) {
            a = ((ClevertecArrayList<?>) collection).getElements();
        } else {
            a = collection.toArray();
        }

        setElements(a);
    }

    /**
     * Returns size of the list
     *
     * @return size of the list
     */
    @Override
    public int size() {
        return getElements().length;
    }

    /**
     * Returns <tt>true</tt> if list is empty
     *
     * @return <tt>true</tt> if list is empty
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns <tt>true</tt> if list contains element
     *
     * @param o element
     * @return <tt>true</tt> if list contains element
     */
    @Override
    public boolean contains(Object o) {
        Object[] elements = getElements();
        return indexOfRange(o, elements, 0, elements.length) >= 0;
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
        Object[] elements = getElements();
        return indexOfRange(o, elements, 0, elements.length);
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
        Object[] elements = getElements();
        return lastIndexOfRange(o, elements, 0, elements.length);
    }

    private static int indexOfRange(Object o, Object[] es, int from, int to) {
        int i;

        if (o == null) {
            for (i = from; i < to; ++i) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (i = from; i < to; ++i) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    private static int lastIndexOfRange(Object o, Object[] es, int from, int to) {
        int i;

        if (o == null) {
            for (i = to - 1; i >= from; --i) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (i = to - 1; i >= from; --i) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }

        return -1;
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
        Object[] elements = getElements();
        return Arrays.copyOf(elements, elements.length);
    }

    /**
     * Returns an array with type equal to received array a
     *
     * @param a an array with a type for the returning array
     * @return an array with type equal to received array a
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        Object[] elements = getElements();
        int length = elements.length;

        if (a.length < length) {
            return (T[]) Arrays.copyOf(elements, length, a.getClass());
        } else {
            System.arraycopy(elements, 0, a, 0, length);
            if (a.length > length) {
                a[length] = null;
            }
            return a;
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
        lock.lock();

        try {
            Object[] elements = getElements();
            int length = elements.length;
            Object[] newElements = Arrays.copyOf(elements, length + 1);
            newElements[length] = e;
            setElements(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }

//    private Object[] grow() {
//        return grow(size + 1);
//    }
//
//    private Object[] grow(int minCapacity) {
//        int oldCapacity = elements.length;
//        if (oldCapacity == 0) {
//            return elements = new Object[DEFAULT_CAPACITY];
//        } else {
//            int newCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
//            return elements = Arrays.copyOf(elements, newCapacity);
//        }
//    }

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
        lock.lock();

        try {
            Object[] elements = getElements();
            int length = elements.length;

            if (index > length || index < 0) {
                throw new IndexOutOfBoundsException("Index:" +
                        index + ", Size: " + length);
            }

            Object[] newElements;
            int numMoved = length - index;
            if (numMoved == 0) {
                newElements = Arrays.copyOf(elements, length + 1);
            } else {
                newElements = new Object[length + 1];
                System.arraycopy(elements, 0, newElements, 0, index);
                System.arraycopy(elements, index, newElements, index + 1, numMoved);
            }
            newElements[index] = element;
            setElements(newElements);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes the first entry of the specified element from this list
     *
     * @param o to be removed from this list
     * @return {@code true} if this list contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        Object[] snapshot = getElements();
        int index = indexOfRange(o, snapshot, 0, snapshot.length);
        return index >= 0 && remove(o, snapshot, index);
    }

    private boolean remove(Object o, Object[] snapshot, int index) {
        final ReentrantLock lock = this.lock;
        lock.lock();

        try {
            Object[] current = getElements();
            int len = current.length;
            if (snapshot != current) findIndex: {
                int prefix = Math.min(index, len);
                for (int i = 0; i < prefix; i++) {
                    if (current[i] != snapshot[i] && eq(o, current[i])) {
                        index = i;
                        break findIndex;
                    }
                }
                if (index >= len)
                    return false;
                if (current[index] == o)
                    break findIndex;
                index = indexOfRange(o, current, index, len);
                if (index < 0)
                    return false;
            }
            Object[] newElements = new Object[len - 1];
            System.arraycopy(current, 0, newElements, 0, index);
            System.arraycopy(current, index + 1,
                    newElements, index,
                    len - index - 1);
            setElements(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }

    private static boolean eq(Object o, Object o1) {
        return Objects.equals(o, o1);
    }

//    private void fastRemove(int index) {
//        int numMoved = size - index - 1;
//        if (numMoved > 0)
//            System.arraycopy(elements, index + 1, elements, index,
//                    numMoved);
//        elements[--size] = null;
//    }

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
        Object[] cs = (c.getClass() == ClevertecArrayList.class) ?
                ((ClevertecArrayList<?>)c).getElements() : c.toArray();
        if (cs.length == 0) {
            return false;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();

        try {
            Object[] elements = getElements();
            int length = elements.length;
            if (length == 0 && cs.getClass() == Object[].class) {
                setElements(cs);
            }
            else {
                Object[] newElements = Arrays.copyOf(elements, length + cs.length);
                System.arraycopy(cs, 0, newElements, length, cs.length);
                setElements(newElements);
            }
            return true;
        } finally {
            lock.unlock();
        }
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
        Object[] cs = c.toArray();
        final ReentrantLock lock = this.lock;
        lock.lock();

        try {
            Object[] elements = getElements();
            int length = elements.length;
            if (index > length || index < 0) {
                throw new IndexOutOfBoundsException("Index: " +
                        index + ", Size: " + length);
            }
            if (cs.length == 0) {
                return false;
            }
            int numMoved = length - index;
            Object[] newElements;
            if (numMoved == 0) {
                newElements = Arrays.copyOf(elements, length + cs.length);
            }
            else {
                newElements = new Object[length + cs.length];
                System.arraycopy(elements, 0, newElements, 0, index);
                System.arraycopy(elements, index,
                        newElements, index + cs.length,
                        numMoved);
            }
            System.arraycopy(cs, 0, newElements, index, cs.length);
            setElements(newElements);
            return true;
        } finally {
            lock.unlock();
        }
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
        lock.lock();

        try {
            setElements(new Object[0]);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns element at specified position
     *
     * @param index - position in list
     * @return element at specified position
     */
    @Override
    public E get(int index) {
        return get(getElements(), index);
    }

    @SuppressWarnings("unchecked")
    private E get(Object[] a, int index) {
        return (E) a[index];
    }

    /**
     * Set element at specified position
     *
     * @param index   - position in list
     * @param element - element for replacing
     * @return replaced element
     */
    @Override
    public E set(int index, E element) {
        final ReentrantLock lock = this.lock;
        lock.lock();

        try {
            Object[] elements = getElements();
            E oldValue = get(elements, index);

            if (oldValue != element) {
                int len = elements.length;
                Object[] newElements = Arrays.copyOf(elements, len);
                newElements[index] = element;
                setElements(newElements);
            } else {
                // Not quite a no-op; ensures volatile write semantics
                setElements(elements);
            }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }
//
//    private void checkIndex(int index) {
//        if (index >= size || index < 0) {
//            throw new IndexOutOfBoundsException(getMessageForIndexOutOfBoundException(index));
//        }
//    }
//
//    private String getMessageForIndexOutOfBoundException(int index) {
//        return "Received index:"
//                + index
//                + " "
//                + "list size:"
//                + size;
//    }
//
//    private void checkIndexForAdd(int index) {
//        if (index > size || index < 0) {
//            throw new IndexOutOfBoundsException(getMessageForIndexOutOfBoundException(index));
//        }
//    }

    @Override
    public E remove(int index) {
        final ReentrantLock lock = this.lock;
        lock.lock();

        try {
            Object[] elements = getElements();
            int length = elements.length;
            E oldValue = get(elements, index);
            int numMoved = length - index - 1;
            if (numMoved == 0)
                setElements(Arrays.copyOf(elements, length - 1));
            else {
                Object[] newElements = new Object[length - 1];
                System.arraycopy(elements, 0, newElements, 0, index);
                System.arraycopy(elements, index + 1, newElements, index,
                        numMoved);
                setElements(newElements);
            }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }

//    @SuppressWarnings("unchecked")
//    private E elementData(int index) {
//        return (E) elements[index];
//    }

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
}
