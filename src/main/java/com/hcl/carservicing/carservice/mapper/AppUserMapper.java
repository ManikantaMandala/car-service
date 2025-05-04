package com.hcl.carservicing.carservice.mapper;

import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.model.AppUser;

public class AppUserMapper {

    public static AppUser toEntity(AppUserDTO userDTO) {
        AppUser user = new AppUser();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());
        user.setContactNumber(userDTO.getContactNumber());
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());

        return user;
    }

}
