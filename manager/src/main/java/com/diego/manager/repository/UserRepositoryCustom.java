package com.diego.manager.repository;

import com.diego.manager.domain.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findByFilters (String name, String contact);
}
