package com.example.AutoSpotter.classes.user;

public interface UserRepository {

    User getUserById(int id);
    
    boolean existsByUsername(String username);

    void save(User user);
}