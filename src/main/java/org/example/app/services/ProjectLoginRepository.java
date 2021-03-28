package org.example.app.services;

import org.example.web.dto.LoginForm;

import java.util.List;

public interface ProjectLoginRepository<T> {
    List<T> retreiveAllUsers();

    void store(T user);

    boolean removeItemByName(String user);

    boolean authenticate(LoginForm loginFrom);
}
