package com.hcl.carservicing.carservice.dao.service;

import com.hcl.carservicing.carservice.model.AppUser;

public interface AppUserDaoService {
    AppUser findByUsername(String username);
    void throwIfUsernameExists(String username);
    void throwIfContactExists(String contactNumber);
    AppUser save(AppUser user);
}
