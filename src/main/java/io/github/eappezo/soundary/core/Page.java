package io.github.eappezo.soundary.core;

import java.util.List;

public class Page<T> {
    private final int page;
    private final int totalPages;
    private final int size;
    private final long total;
    private final List<T> content;

    public Page(int page, int size, long total, List<T> content) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.content = content;
        this.totalPages = (int) Math.ceil((double) total / size);
    }

    public int page() {
        return page;
    }

    public int totalPages() {
        return totalPages;
    }

    public int size() {
        return size;
    }

    public long total() {
        return total;
    }

    public List<T> content() {
        return content;
    }
}
