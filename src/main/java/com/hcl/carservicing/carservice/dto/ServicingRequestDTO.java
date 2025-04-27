package com.hcl.carservicing.carservice.dto;

import java.time.LocalDate;

import com.hcl.carservicing.carservice.enums.RequestStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class ServicingRequestDTO {

    private Long id;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Please enter a date that is today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "Please enter a date that is today or in the future")
    private LocalDate endDate;

    private RequestStatus status;

    @NotNull(message = "Username is mandatory")
    private String username;

    private Long deliveryBoyId;

    @NotNull(message = "ServiceCenterServiceType Id is mandatory")
    private Long serviceId;

    private Long serviceCenterId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getDeliveryBoyId() {
		return deliveryBoyId;
	}

	public void setDeliveryBoyId(Long deliveryBoyId) {
		this.deliveryBoyId = deliveryBoyId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getServiceCenterId() {
		return serviceCenterId;
	}

	public void setServiceCenterId(Long serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}
}
