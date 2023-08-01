package com.example.AutoSpotter.classes.user;

public interface UserRepository {

    User getUserById(int id);

    void save(User user);

    void deleteUser (int userId);
    
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByUsernameAndPassword(String username, String password);

    User findByConfirmationToken(String token);

    void updateEmailVerification(User user);

    void updatePassword(User user);
}