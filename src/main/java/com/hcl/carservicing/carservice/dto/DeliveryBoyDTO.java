package com.hcl.carservicing.carservice.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DeliveryBoyDTO {
	@NotBlank(message = "Name is mandatory")
	@Size(min = 4, max = 100, message = "Name must be between 4 and 100 characters")
	private String name;

	@NotBlank(message = "Contact Number is mandatory")
	@Pattern(regexp = "^[0-9]{10}$", message = "Contact Number must be only 10 digits")
	private String contactNumber;

	@NotNull(message = "Service Center ID is mandatory")
	private Long serviceCenterId;

	private List<Long> serviceRequestsId;

	public List<Long> getServiceRequestsId() {
		return serviceRequestsId;
	}

	public void setServiceRequestsId(List<Long> serviceRequestsId) {
		this.serviceRequestsId = serviceRequestsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Long getServiceCenterId() {
		return serviceCenterId;
	}

	public void setServiceCenterId(Long serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}
}

