package org.example.web.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookToRemove {

    @NotNull
    private Integer id;
    @NotEmpty
    @Size(max = 50)
    private String author;
    @NotEmpty
    @Size(max = 50)
    private String title;
    @NotNull
    @Digits(integer = 4,fraction = 0)
    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
