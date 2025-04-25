package com.hcl.carservicing.carservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

	Optional<AppUser> findByContactNumber(String contactNumber);

	// TODO: remove this because it is not used
	AppUser save(AppUserDTO userDTO);
}

