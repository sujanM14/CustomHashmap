package com.example.CustomHashmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memory")
public class CustomHashmapController {

    @Autowired
    private CustomHashmapService memoryManagerService;

    @PostMapping("/init")
    public String init(@RequestParam int pageSize, @RequestParam int numPages) {
        memoryManagerService.init(pageSize, numPages);
        return "Initialized with page size " + pageSize + " and number of pages " + numPages;
    }

    @GetMapping("/get/{key}")
    public int get(@PathVariable int key) {
        return memoryManagerService.get(key);
    }

    @PostMapping("/put")
    public String put(@RequestParam int key, @RequestParam int value) {
        memoryManagerService.put(key, value);
        return "Inserted key: " + key + ", value: " + value;
    }

    @DeleteMapping("/delete/{key}")
    public String delete(@PathVariable int key) {
        memoryManagerService.delete(key);
        return "Deleted key: " + key;
    }

    @GetMapping("/dump")
    public String dump() {
        return "Memory Dump: " + memoryManagerService.dump();
    }
}
