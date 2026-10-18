package io.r4v3chain.backend.showcase.news.store;

import io.r4v3chain.backend.showcase.model.NewsItem;

import java.util.List;

public interface NewsItemStore {

    List<NewsItem> findAllPersisted();

    void save(NewsItem item);

    void deleteById(String id);

    void saveAll(List<NewsItem> items);
}
