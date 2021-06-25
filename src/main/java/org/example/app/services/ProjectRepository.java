package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    List<T> searchItem(Object bookToSearch);

    boolean removeItem(Integer bookIdToRemove);

    List<String> getFiles();

    void addFile(String fileName);
}
