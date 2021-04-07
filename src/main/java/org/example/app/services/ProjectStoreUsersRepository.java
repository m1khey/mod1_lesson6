package org.example.app.services;

import java.util.List;

public interface ProjectStoreUsersRepository<T> {
    List<T> retreiveAllUsers();

    void store(T user);

    boolean removeItemByName(String user);
}
