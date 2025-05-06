package com.hcl.carservicing.carservice.config;

import com.hcl.carservicing.carservice.dao.service.AppUserDaoService;
import com.hcl.carservicing.carservice.model.AppUser;
import com.hcl.carservicing.carservice.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AppUserRepository userRepository;
    private final AppUserDaoService appUserDaoService;

    public CustomUserDetailsService(
            AppUserRepository userRepository,
                                    AppUserDaoService appUserDaoService) {
        this.userRepository = userRepository;
        this.appUserDaoService = appUserDaoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserDaoService.findByUsername(username);

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole().toString());

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}

