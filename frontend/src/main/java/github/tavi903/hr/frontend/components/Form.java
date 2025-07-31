package github.tavi903.hr.frontend.components;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.util.Pair;

import java.util.List;

@Builder
@Getter
public class Form {

    private String title;
    private String error;
    private String formAction;
    private List<FormElement> elements;
    private String json;

    public Form withJson(String json) {
        this.json = json;
        return this;
    }


    @SuperBuilder
    @Getter
    public static class Label extends FormElement {
        public static final String tag = "label";
        private String innerText;

        @Override
        public String getTag() {
            return Label.tag;
        }
    }

    @SuperBuilder
    @Getter
    public static class Input extends FormElement {
        public static final String tag = "input";
        private Type type;
        private String value;
        private String name;

        @Override
        public String getTag() {
            return Input.tag;
        }

        @Getter
        public enum Type {
            TEXT,
            DATE;
        }
    }

    @SuperBuilder
    @Getter
    public static class Select extends FormElement {
        public static final String tag = "select";
        private List<Pair<String, String>> options;
        private String name;

        @Override
        public String getTag() {
            return Select.tag;
        }
    }

}
