package com.hcl.carservicing.carservice.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.hcl.carservicing.carservice.enums.RequestStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "servicing_request")
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@CreationTimestamp
	private LocalDateTime createdAt;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Please enter the date which is greater than previous day’s date")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "Please enter the date which is greater than start date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

 	@ManyToOne
 	@NotNull(message = "Username is mandaory")
 	private AppUser user;

 	@ManyToOne
 	private DeliveryBoy deliveryBoy;

 	@ManyToOne
 	@NotNull(message = "ServiceCenterServiceType Id is mandaory")
 	private ServiceCenterServiceType service;

 	@ManyToOne
 	private ServiceCenter serviceCenter;
    
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public DeliveryBoy getDeliveryBoy() {
		return deliveryBoy;
	}

	public void setDeliveryBoy(DeliveryBoy deliveryBoy) {
		this.deliveryBoy = deliveryBoy;
	}

	public ServiceCenterServiceType getService() {
		return service;
	}

	public void setService(ServiceCenterServiceType service) {
		this.service = service;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public ServiceCenter getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(ServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}
}

