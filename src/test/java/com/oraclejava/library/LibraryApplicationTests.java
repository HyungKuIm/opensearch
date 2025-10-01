package com.oraclejava.library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LibraryApplicationTests {

    @Autowired
    private LibrarySearchService librarySearchService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("검색 성공")
    public void search() {
        // Given
        List<ITBook> books = librarySearchService.search("스프링");
        // When

        // Then
        assert !books.isEmpty();
    }

}
