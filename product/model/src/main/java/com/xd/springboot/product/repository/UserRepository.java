package com.xd.springboot.product.repository;

import com.xd.springboot.product.domain.UserDO;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class UserRepository {

    private static final ConcurrentMap<Long, String> REPOSITORY = new ConcurrentHashMap<>();

    public boolean save(long id, String name) {
        return REPOSITORY.put(id, name) == null;
    }

    public Set<UserDO> findAll() {
        return Collections.EMPTY_SET;
    }

}
