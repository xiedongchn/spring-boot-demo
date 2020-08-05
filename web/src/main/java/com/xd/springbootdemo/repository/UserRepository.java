package com.xd.springbootdemo.repository;

import com.xd.springbootdemo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class UserRepository {

    private static final ConcurrentMap<Long, String> repository = new ConcurrentHashMap<>();

    public boolean save(long id, String name) {
        return repository.put(id, name) == null;
    }

    public Collection<User> findAll() {
        return Collections.EMPTY_SET;
    }

}
