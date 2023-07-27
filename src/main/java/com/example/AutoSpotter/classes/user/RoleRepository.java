package com.example.AutoSpotter.classes.user;

public interface RoleRepository {
    Role getRoleById(int roleId);
    Role findByName(String name);
}
