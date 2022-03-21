package ru.clevertec.collections;

import by.home.collections.ClevertecArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClevertecArrayListTest {

    private ClevertecArrayList<String> clevertecArrayList = new ClevertecArrayList<>();

    @BeforeEach
    public void initializeCustomArrayList() {
        clevertecArrayList.clear();

        for (int i = 0; i < 100; i++) {
            clevertecArrayList.add("elem" + i);
        }
    }

    @Test
    public void testSize() {
        assertEquals(100, clevertecArrayList.size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(clevertecArrayList.isEmpty());
    }

    @Test
    public void testContains() {
        assertTrue(clevertecArrayList.contains("elem78"));
    }

    @Test
    public void testContainsIfElementNotExists() {
        assertFalse(clevertecArrayList.contains("elem101"));
    }

    @Test
    public void testIndexOf() {
        assertEquals(56, clevertecArrayList.indexOf("elem56"));
    }

    @Test
    public void testIndexOfIfElementNotContains() {
        assertEquals(-1, clevertecArrayList.indexOf("elem101"));
    }

    @Test
    public void testLastIndexOf() {
        assertEquals(98, clevertecArrayList.lastIndexOf("elem98"));
    }

    @Test
    public void testLastIndexOfIfElementNotContains() {
        assertEquals(-1, clevertecArrayList.lastIndexOf("elem101"));
    }

    @Test
    public void testGet() {
        assertEquals("elem99", clevertecArrayList.get(99));
    }

    @Test
    public void testAddWithOneParameters() {
        clevertecArrayList.add("elem101");
        assertEquals("elem101", clevertecArrayList.get(100));
    }

    @Test
    public void testAddWithTwoParameters() {
        clevertecArrayList.add(5, "elem5test");
        assertEquals("elem5test", clevertecArrayList.get(5));
    }

    @Test
    public void testRemoveWithObjectParameter() {
        clevertecArrayList.remove("elem5test");
        assertEquals(100, clevertecArrayList.size());
    }

    @Test
    public void testRemoveWithIntParameter() {
        clevertecArrayList.remove(1);
        assertEquals(99, clevertecArrayList.size());
    }

    @Test
    public void testAddAllWithOneParameter() {
        clevertecArrayList.addAll(Arrays.asList("elem101test", "elem102test", "elem103test"));
        assertEquals(103, clevertecArrayList.size());
    }

    @Test
    public void testAddAllWithTwoParameters() {
        clevertecArrayList.addAll(56,
                Arrays.asList("elem101test", "elem102test", "elem103test"));
        assertEquals("elem101test", clevertecArrayList.get(56));
    }

    @Test
    public void testSet() {
        clevertecArrayList.set(92, "elem1000test");
        assertEquals("elem1000test", clevertecArrayList.get(92));
    }

    @Test
    public void testClear() {
        clevertecArrayList.clear();
        assertEquals(0, clevertecArrayList.size());
    }
}
