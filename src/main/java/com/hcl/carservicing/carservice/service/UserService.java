package com.hcl.carservicing.carservice.service;

import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.dto.UserLoginDTO;

public interface UserService {
    void register(AppUserDTO userDTO);
    UserLoginDTO login(String username, String password);
}