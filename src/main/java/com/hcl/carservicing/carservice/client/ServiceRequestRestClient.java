package com.hcl.carservicing.carservice.client;

import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ServiceRequestRestClient {

    private final RestTemplate restTemplate;

    public ServiceRequestRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ServiceRequestDTO> fetchServiceRequests(String username) {
        String url = "http://localhost:8080/api/v1/service-request/user/" + username;
        ResponseEntity<ServiceRequestDTO[]> responseEntity = restTemplate.getForEntity(url , ServiceRequestDTO[].class);

        assert responseEntity.getBody() != null;
        return List.of(responseEntity.getBody());
    }
}
