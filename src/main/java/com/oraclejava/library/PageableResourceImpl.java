package com.oraclejava.library;

import java.util.List;

import lombok.Data;

@Data
public class PageableResourceImpl implements PageableResource{

    int page = 1;

    int totalPages;

    List<?> contents;

    // 메시지
    String message;

    public PageableResourceImpl() {}

    public PageableResourceImpl(List<? extends IEntity> contents, int page, int totalPages) {
        this.contents = contents;
        this.page = page;
        this.totalPages = totalPages;
    }

}
