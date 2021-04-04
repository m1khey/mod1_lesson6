package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    void searchItem(Object bookToSearch);

    boolean removeItem(Object bookToRemove);
}
