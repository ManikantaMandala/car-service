package com.hcl.carservicing.carservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ServiceTypeDTO {
	private Long id;
	
    @NotBlank(message = "Service name is required")
    @Size(min = 3, max = 100, message = "Service name must be between 3 and 100 characters")
    private String serviceName;

    @NotBlank(message = "Service Description is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
