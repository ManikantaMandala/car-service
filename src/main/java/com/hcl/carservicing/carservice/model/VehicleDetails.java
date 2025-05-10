package com.hcl.carservicing.carservice.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class VehicleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vehicleName;

    @ManyToOne
    private AppUser appUser;

    @OneToMany(
        mappedBy = "vehicleDetails",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private List<ServiceRequest> serviceRequests;

    public VehicleDetails() {
    }

    public VehicleDetails(AppUser appUser,String vehicleName) {
        this.appUser = appUser;
        this.vehicleName = vehicleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public List<ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(List<ServiceRequest> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

}
