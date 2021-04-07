package org.example.app.services;

import org.example.web.dto.LoginForm;

import java.util.List;

public interface ProjectLoginRepository<T> {

    boolean authenticate(LoginForm loginFrom);
}
