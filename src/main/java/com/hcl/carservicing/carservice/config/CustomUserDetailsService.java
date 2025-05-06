package com.hcl.carservicing.carservice.config;

import com.hcl.carservicing.carservice.dao.service.AppUserDaoService;
import com.hcl.carservicing.carservice.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AppUserDaoService appUserDaoService;

    public CustomUserDetailsService(AppUserDaoService appUserDaoService) {
        this.appUserDaoService = appUserDaoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loading user by username {}", username);
        AppUser user = appUserDaoService.findByUsername(username);

        logger.info("creating authorities of user");
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole().toString());

        logger.info("creating new user and returning it");
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}

