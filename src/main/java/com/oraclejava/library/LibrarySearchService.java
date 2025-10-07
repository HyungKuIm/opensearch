package com.oraclejava.library;

import lombok.AllArgsConstructor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LibrarySearchService {
    private final OpenSearchClient openSearchClient;

    public List<ITBook> search(String keyword) {
        List<ITBook> books = new ArrayList<>();
        //ITBook book = new ITBook();
        try {
            // 대소문자 구분 하지 않기위해서 match에서 term으로 변경
            //google.com/search?q=opensearch+ignore+case&rlz=1C1PNKB_enKR1165KR1165&gs_lcrp=EgZjaHJvbWUqCQgCEAAYExiABDIGCAAQRRg5MgkIARAAGBMYgAQyCQgCEAAYExiABDIJCAMQABgTGIAEMgkIBBAAGBMYgAQyCAgFEAAYExgeMggIBhAAGBMYHjIICAcQABgTGB4yCAgIEAAYExgeMggICRAAGBMYHtIBCDc3MjBqMGo3qAIAsAIA&sourceid=chrome&ie=UTF-8&udm=50&fbs=AIIjpHxAboEh8T4AmOafDyCTZyImbu7ua2q4ZFqx4qKGzpR0xtJRrGTW7f0pXdPdIGyseoZVBqoYBwXt44Lz2w1BZVtUi404Tked3a57zoQmFfLxI0IRIrneSuvLcHHDAqHG97za1v8yXYS2NijiEh1xRJJ628--AbHWJDJMl7HL8du0O8Qn8P0DbKCD4Ofn6QXFHUBTYwkrWcVsm8hkAHa2QF2dIe5hIm0sZTfD58oqTkC9uUyacd0&ved=2ahUKEwjiiMej9JGQAxVloa8BHQavD6cQ0NsOegQIPBAA&aep=10&ntc=1
            SearchRequest request = SearchRequest.of(searchRequest ->
                    searchRequest.index("library")
                            .query(query ->
                                    query.term(match ->
                                            match.field("title")
                                                .value(q -> q.stringValue(keyword))
                                                .caseInsensitive(true))));

            SearchResponse<ITBook> searchResponse =
                    openSearchClient.search(request, ITBook.class);

            List<Hit<ITBook>> hits = searchResponse.hits().hits();
            System.out.println("Hit count: " + hits.size());
            for (Hit<ITBook> hit : hits) {
                books.add(hit.source());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return books;
    }
}
