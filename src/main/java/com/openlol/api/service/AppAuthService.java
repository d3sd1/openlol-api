package com.openlol.api.service;

import com.openlol.api.model.User;

public class AppAuthService {
    private static User user = new User("1", "nombre espacial");

    public User getUser(String id) {
        if (!id.equals("1")) {
            return null;
        }
        return user;
    }

    public void updateUser(User newUser) {
        user = newUser;
    }
}
