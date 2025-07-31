package github.tavi903.hr.frontend.components;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class FormElement {
    private String id;
    public abstract String getTag();
    public String getId() {
        return this.id;
    }
}
