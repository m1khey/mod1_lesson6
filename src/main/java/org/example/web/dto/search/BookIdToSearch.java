package org.example.web.dto.search;

import javax.validation.constraints.NotNull;

public class BookIdToSearch {

    @NotNull
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
