package com.example.CustomHashmap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

class CustomHashmapServiceTest {

    @InjectMocks
    private CustomHashmapService customHashmapService;

    @Mock
    private CustomHashmap memoryManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInit() {
        int pageSize = 10;
        int numPages = 5;

        // Call the method
        customHashmapService.init(pageSize, numPages);

        // Verify that the init method of memoryManager is called
        verify(memoryManager, times(1)).init(pageSize, numPages);
    }

    @Test
    void testPut() {
        int key = 1;
        int value = 100;

        // Call the method
        customHashmapService.put(key, value);

        // Verify that the put method of memoryManager is called
        verify(memoryManager, times(1)).put(key, value);
    }

    @Test
    void testGet() {
        int key = 1;
        int expectedValue = 100;

        // Mock the behavior of memoryManager
        when(memoryManager.get(key)).thenReturn(expectedValue);

        // Call the method and assert the result
        int result = customHashmapService.get(key);
        assertEquals(expectedValue, result);

        // Verify that the get method of memoryManager is called
        verify(memoryManager, times(1)).get(key);
    }

    @Test
    void testDelete() {
        int key = 1;

        // Call the method
        customHashmapService.delete(key);

        // Verify that the delete method of memoryManager is called
        verify(memoryManager, times(1)).delete(key);
    }

    @Test
    void testDump() {
        String expectedDump = "Dump data";

        // Mock the behavior of memoryManager
        when(memoryManager.dump()).thenReturn(expectedDump);

        // Call the method and assert the result
        String result = customHashmapService.dump();
        assertEquals(expectedDump, result);

        // Verify that the dump method of memoryManager is called
        verify(memoryManager, times(1)).dump();
    }
}
