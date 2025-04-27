package com.hcl.carservicing.carservice.config;

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
    private static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AppUserRepository userRepository;

    public CustomUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from database
        Optional<AppUser> user= userRepository.findByUsername(username);

        if (user.isEmpty()) {
            logger.warn("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        // Convert roles into authorities
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.get().getRole().name());

//        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), authorities);
        return new User(user.get().getUsername(), user.get().getPassword(), authorities);
    }
}

