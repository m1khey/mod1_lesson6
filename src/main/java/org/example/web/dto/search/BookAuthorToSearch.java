package org.example.web.dto.search;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookAuthorToSearch {

    @NotEmpty
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
