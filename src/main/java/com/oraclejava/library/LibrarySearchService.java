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
        ITBook book = new ITBook();
        try {
            SearchRequest request = SearchRequest.of(searchRequest ->
                    searchRequest.index("library")
                            .query(query ->
                                    query.match(match ->
                                            match.field("title").query(q -> q.stringValue(keyword)))));

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
