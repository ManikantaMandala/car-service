package com.hcl.carservicing.carservice.service;

import com.hcl.carservicing.carservice.dto.AppUserDTO;

public interface UserService {
    void register(AppUserDTO userDTO);
    void login(String userId, String password);
}