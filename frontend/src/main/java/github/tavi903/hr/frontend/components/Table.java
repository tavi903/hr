package github.tavi903.hr.frontend.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
public class Table<R> {
    private String title;
    private List<String> headers;
    private final List<Row> rows;
    private String json;
    private String addButtonText;
    private boolean canAddRow;
    private boolean canBeModified;
    private boolean isSearchEnabled;
    private String filename;
    private String pathToDownloadCsv;
    private Pagination pagination;
    private Form form;
    private Form searchForm;


    private Table(List<R> elements, Function<R, String> getId, List<Function<R, String>> columnGetters) {
        // populate Rows
        rows = new ArrayList<>(elements.size());
        for (R element: elements) {
            List<String> columns = columnGetters.stream().map(getter -> getter.apply(element)).toList();
            rows.add(new Row(getId.apply(element), columns.toArray(String[]::new)));
        }
    }

    public static <R> Table<R> of(List<R> elements, Function<R, String> getId, List<Function<R, String>> columnGetters) {
        return new Table<>(elements, getId, columnGetters);
    }

    public Table<R> andIsModifiable() {
        this.canBeModified = true;
        return this;
    }

    public Table<R> andCanAddRow() {
        this.canAddRow = true;
        return this;
    }

    public Table<R> andIsSearchEnabled() {
        this.isSearchEnabled = true;
        return this;
    }

    public Table<R> withSearchForm(Form form) {
        this.searchForm = form;
        return this;
    }

    public Table<R> withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public Table<R> withPathToDownloadCsv(String path) {
        this.pathToDownloadCsv = path;
        return this;
    }

    public Table<R> withAddButtonText(String addButtonText) {
        this.addButtonText = addButtonText;
        return this;
    }

    public Table<R> withPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    public Table<R> withTitle(String title) {
        this.title = title;
        return this;
    }

    public Table<R> withHeaders(List<String> headers) {
        this.headers = headers;
        return this;
    }

    public Table<R> withJson(String json) {
        this.json = json;
        return this;
    }

    @SuppressWarnings("all")
    public Table<R> withForm(Form form) {
        this.form = form;
        return this;
    }

    @Getter
    public static class Pagination {
        private final Integer pageSize;
        private final Integer currentPage;
        private final Long totalElements;
        private final String path;
        private List<Integer> pages;
        private boolean isFirstPageMultipleOfFive;
        private boolean isLastPageMultipleOfFivePlusOne;

        private Pagination(Integer pageSize, Integer currentPage, Long totalElements, String path) {
            this.pageSize = pageSize;
            this.currentPage = currentPage;
            this.totalElements = totalElements;
            this.path = path;
        }

        public static Pagination of(Integer pageSize, Integer currentPage, Long totalElements, String path) {
            Pagination pagination = new Pagination(pageSize, currentPage, totalElements, path);
            pagination.setPages(pagination.computePages());
            if (!pagination.getPages().isEmpty()) {
                pagination.setFirstPageMultipleOfFive(pagination.getPages().getFirst() != 0 && (pagination.getPages().getFirst() + 1) % 5 == 0);
                if (pagination.getPages().size() >= 6)
                    pagination.setLastPageMultipleOfFivePlusOne(pagination.getPages().getLast() != 1 && (pagination.getPages().getLast()+1) % 5 == 1);
            }
            return pagination;
        }

        private void setFirstPageMultipleOfFive(boolean firstPageMultipleOfFive) {
            isFirstPageMultipleOfFive = firstPageMultipleOfFive;
        }

        private void setLastPageMultipleOfFivePlusOne(boolean lastPageMultipleOfFivePlusOne) {
            isLastPageMultipleOfFivePlusOne = lastPageMultipleOfFivePlusOne;
        }

        private void setPages(List<Integer> pages) {
            this.pages = pages;
        }

        public List<Integer> getInternalPages() {
            int firstElementPosition = 0;
            if (isFirstPageMultipleOfFive)
                firstElementPosition = 1;
            int lastElementPositionExclusive = pages.size();
            if (isLastPageMultipleOfFivePlusOne)
                lastElementPositionExclusive--;
            return pages.subList(firstElementPosition, lastElementPositionExclusive);
        }

        private List<Integer> computePages() {
            long maxPage = (totalElements % pageSize) == 0 ? totalElements / pageSize : totalElements / pageSize + 1;
            if (maxPage == 1)
                return List.of();
            if (maxPage <= 5)
                return generatePagesFromTo(0, maxPage - 1);
            int firstPage = (currentPage / 5) > 0 ? currentPage / 5 * 5 - 1 : 0;
            long lastPage = Math.min(firstPage == 0 ? firstPage + 5 : firstPage + 6, maxPage - 1);
            return generatePagesFromTo(firstPage, lastPage);
        }

        private static ArrayList<Integer> generatePagesFromTo(Integer firstPage, long lastPage) {
            ArrayList<Integer> pages = new ArrayList<>();
            while (firstPage <= lastPage) {
                pages.add(firstPage);
                firstPage++;
            }
            return pages;
        }
    }

    @Data
    @AllArgsConstructor
    private static class Row {
        private String id;
        private String[] columnValues;
    }

}
