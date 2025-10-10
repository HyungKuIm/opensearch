package com.oraclejava.library;

public interface PageableResource {
    int getPage();

    int getTotalPages();

    void setPage(int page);

    void setTotalPages(int totalPages);
}
