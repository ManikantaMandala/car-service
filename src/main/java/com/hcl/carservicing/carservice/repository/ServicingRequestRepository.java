package com.hcl.carservicing.carservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.carservicing.carservice.model.ServicingRequest;

@Repository
public interface ServicingRequestRepository extends JpaRepository<ServicingRequest, Long> {
    List<ServicingRequest> findByUserUsername(String username);
}
