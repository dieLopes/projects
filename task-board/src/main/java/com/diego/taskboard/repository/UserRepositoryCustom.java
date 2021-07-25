package com.diego.taskboard.repository;

import com.diego.taskboard.domain.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findByFilters (String name, String contact);
}
