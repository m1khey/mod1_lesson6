package org.example.app.services;

import org.example.web.dto.BookToSearch;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    List<T> searchItem(Integer bookToSearchById, String bookToSearchByAutor,String bookToSearchByTitle
            ,Integer bookToSearchBySize);

    boolean removeItem(Integer bookIdToRemove);

    List<String> getFiles();

    void addFile(String fileName);
}
