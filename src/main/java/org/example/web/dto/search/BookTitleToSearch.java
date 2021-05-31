package org.example.web.dto.search;

import javax.validation.constraints.NotEmpty;

public class BookTitleToSearch {

    @NotEmpty
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
