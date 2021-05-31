package org.example.web.dto.search;

import javax.validation.constraints.NotNull;

public class BookSizeToSearch {

    @NotNull
    private Integer size;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
