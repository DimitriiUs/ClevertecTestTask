package ru.clevertec.collections;

import by.home.collections.ClevertecLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClevertecLinkedListTest {

    private ClevertecLinkedList<String> clevertecLinkedList = new ClevertecLinkedList<>();

    @BeforeEach
    public void initializeCustomArrayList() {
        clevertecLinkedList.clear();

        for (int i = 0; i < 100; i++) {
            clevertecLinkedList.add("elem" + i);
        }
    }

    @Test
    public void testSize() {
        assertEquals(100, clevertecLinkedList.size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(clevertecLinkedList.isEmpty());
    }

    @Test
    public void testContains() {
        assertTrue(clevertecLinkedList.contains("elem78"));
    }

    @Test
    public void testContainsIfElementNotExists() {
        assertFalse(clevertecLinkedList.contains("elem101"));
    }

    @Test
    public void testIndexOf() {
        assertEquals(56, clevertecLinkedList.indexOf("elem56"));
    }

    @Test
    public void testIndexOfIfElementNotContains() {
        assertEquals(-1, clevertecLinkedList.indexOf("elem101"));
    }

    @Test
    public void testLastIndexOf() {
        assertEquals(98, clevertecLinkedList.lastIndexOf("elem98"));
    }

    @Test
    public void testLastIndexOfIfElementNotContains() {
        assertEquals(-1, clevertecLinkedList.lastIndexOf("elem101"));
    }

    @Test
    public void testGet() {
        assertEquals("elem99", clevertecLinkedList.get(99));
    }

    @Test
    public void testAddWithOneParameters() {
        clevertecLinkedList.add("elem101");
        assertEquals("elem101", clevertecLinkedList.get(100));
    }

    @Test
    public void testAddWithTwoParameters() {
        clevertecLinkedList.add(5, "elem5test");
        assertEquals("elem5test", clevertecLinkedList.get(5));
    }

    @Test
    public void testRemoveWithObjectParameter() {
        clevertecLinkedList.remove("elem5test");
        assertEquals(100, clevertecLinkedList.size());
    }

    @Test
    public void testRemoveWithIntParameter() {
        clevertecLinkedList.remove(1);
        assertEquals(99, clevertecLinkedList.size());
    }

    @Test
    public void testAddAllWithOneParameter() {
        clevertecLinkedList.addAll(Arrays.asList("elem101test", "elem102test", "elem103test"));
        assertEquals(103, clevertecLinkedList.size());
    }

    @Test
    public void testAddAllWithTwoParameters() {
        clevertecLinkedList.addAll(56,
                Arrays.asList("elem101test", "elem102test", "elem103test"));
        assertEquals("elem101test", clevertecLinkedList.get(56));
    }

    @Test
    public void testSet() {
        clevertecLinkedList.set(92, "elem1000test");
        assertEquals("elem1000test", clevertecLinkedList.get(92));
    }

    @Test
    public void testClear() {
        clevertecLinkedList.clear();
        assertEquals(0, clevertecLinkedList.size());
    }
}
