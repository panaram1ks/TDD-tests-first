package com.parom.data;

import com.parom.model.User;

public interface UserRepository {

    boolean save(User user);
}
