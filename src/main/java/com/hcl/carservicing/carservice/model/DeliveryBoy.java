package com.hcl.carservicing.carservice.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "delivery_boy")
public class DeliveryBoy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 4, max = 100, message = "Name must be between 4 and 100 characters")
    private String name;
    
    @NotBlank(message = "Contact Number is mandatory")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact Number must be only 10 digits")
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "service_center_id")
    private ServiceCenter serviceCenter;

    @OneToMany(mappedBy = "deliveryBoy", 
    		cascade = { CascadeType.PERSIST, CascadeType.MERGE },
			fetch = FetchType.EAGER
	)
    private List<ServiceRequest> serviceRequests;

    private Boolean available = true;

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public List<ServiceRequest> getServicingRequest() {
		return serviceRequests;
	}

	public void setServicingRequest(List<ServiceRequest> serviceRequest) {
		this.serviceRequests = serviceRequest;
	}

	public ServiceCenter getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(ServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

}

