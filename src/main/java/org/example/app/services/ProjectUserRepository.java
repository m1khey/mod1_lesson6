package org.example.app.services;

import java.util.List;

public interface ProjectUserRepository<T> {
    List<T> retreiveAllUsers();

    void store(T user);

    boolean removeItemByName(String user);
}
