package util;


import java.util.ArrayList;

/**
 * Last recently used cache structure
 * @param <T> class type of collection element
 */
public class LRUCache<T> {


    /**
     * inner collection of elements
     */
    private ArrayList<T> array = new ArrayList<>();

    private final int maxSize;

    /**
     *
     * @param maxSize maximum size of collection, can't be lower than zero or equal to it
     * @throws IllegalArgumentException if {@link #maxSize} is lower than zero or equal to it
     */
    public LRUCache(int maxSize) {
        if (maxSize <= 0)
            throw new IllegalArgumentException("Illegal capacity: %d".formatted(maxSize));
        this.maxSize = maxSize;
    }


    /**
     * if collection size equals {@link #maxSize} first element of collection will be removed, then new element will be added
     * @param element that supposed to be added to collection
     */
    public void add(T element) {
        if (array.size() == maxSize)
            array.remove(0);
        array.add(element);
    }

    /**
     * clears collection
     */
    public void clear() {
        this.array = new ArrayList<>();
    }

    /**
     * returns inner collection
     * @return {@link #array}
     */
    public ArrayList<T> getArray() {
        return this.array;
    }

    /**
     *
     * @return current size of inner collection
     */
    public int size() {
        return this.array.size();
    }
}
