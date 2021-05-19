package org.example.web.dto.remove;

import javax.validation.constraints.NotEmpty;


public class BookAuthorToRemove {

    @NotEmpty
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
