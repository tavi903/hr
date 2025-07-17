package github.tavi903.hr.frontend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
public class Table<R> {
    private String title;
    private final List<Row> rows;
    private String json;
    private boolean canBeModified;

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

    public Table<R> withTitle(String title) {
        this.title = title;
        return this;
    }

    public Table<R> withJson(String json) {
        this.json = json;
        return this;
    }

    @Data
    @AllArgsConstructor
    private static class Row {
        private String id;
        private String[] columnValues;
    }

}
