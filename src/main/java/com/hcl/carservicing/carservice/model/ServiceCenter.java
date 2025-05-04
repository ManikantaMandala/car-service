package com.hcl.carservicing.carservice.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "service_center")
public class ServiceCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 4, max = 30, message = "Name must be between 4 and 30 characters")
    private String name;
    
    @NotBlank(message = "Address is mandatory")
    @Size(min = 10, max = 255, message = "Address must be between 10 and 255 characters")
    private String address;
    
    @NotNull(message = "Rating is mandatory")
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0.0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must be at most 5.0")
    private Double rating;

    @OneToMany(mappedBy = "serviceCenter", 
    		cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<ServiceRequest> serviceRequests;

    @OneToMany(mappedBy = "serviceCenter", fetch = FetchType.EAGER,
    		cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<ServiceCenterServiceType> serviceCenterServiceTypes;

    @OneToMany(mappedBy = "serviceCenter", 
    		cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<DeliveryBoy> deliveryBoys;
	
	private Boolean available = true; // Default value set here

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public List<ServiceRequest> getServicingRequests() {
		return serviceRequests;
	}

	public void setServicingRequests(List<ServiceRequest> serviceRequests) {
		this.serviceRequests = serviceRequests;
	}

	public List<ServiceCenterServiceType> getServiceCenterServiceTypes() {
		return serviceCenterServiceTypes;
	}

	public void setServiceCenterServiceTypes(List<ServiceCenterServiceType> serviceCenterServiceTypes) {
		this.serviceCenterServiceTypes = serviceCenterServiceTypes;
	}

	public List<DeliveryBoy> getDeliveryBoys() {
		return deliveryBoys;
	}

	public void setDeliveryBoys(List<DeliveryBoy> deliveryBoys) {
		this.deliveryBoys = deliveryBoys;
	}
}

