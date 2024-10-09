package com.example.CustomHashmap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomHashmapService {

    @Autowired
    private CustomHashmap memoryManager;

    public void init(int pageSize, int numPages) {
        memoryManager.init(pageSize, numPages);
    }

    public int get(int key) {
        return memoryManager.get(key);
    }

    public void put(int key, int value) {
        memoryManager.put(key, value);
    }

    public void delete(int key) {
        memoryManager.delete(key);
    }

    public String dump() {
        return memoryManager.dump();
    }
}
