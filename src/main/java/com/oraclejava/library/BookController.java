package com.oraclejava.library;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:3000",
    methods = {RequestMethod.GET, RequestMethod.POST},
    maxAge = 3600,
    allowedHeaders = "*")
public class BookController {

    private final LibrarySearchService librarySearchService;

    @GetMapping("/list")
    public List<ITBook> listOfBook(@RequestParam String title,
            @RequestParam(required = false, defaultValue = "1") int page) {
        log.info("title: " + title);
        int pageSize = 9;
        int from = (page - 1) * pageSize;

        return librarySearchService.search(title, from, pageSize);
    }
    
}
