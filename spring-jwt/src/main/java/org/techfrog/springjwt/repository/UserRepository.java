package org.techfrog.springjwt.repository;

import org.springframework.stereotype.Repository;
import org.techfrog.springjwt.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private Map<String, User> users = new ConcurrentHashMap<>();

    public void createUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User findByUsername(String username) {
        return users.get(username);
    }
}
