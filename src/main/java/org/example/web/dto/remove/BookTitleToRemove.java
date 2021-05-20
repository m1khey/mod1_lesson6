package org.example.web.dto.remove;

import javax.validation.constraints.NotEmpty;

public class BookTitleToRemove {

    @NotEmpty
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
