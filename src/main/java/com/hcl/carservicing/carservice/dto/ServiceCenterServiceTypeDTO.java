package com.hcl.carservicing.carservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ServiceCenterServiceTypeDTO {
    private Long id;

    @NotNull(message = "Service Center is mandatory")
    private Long serviceCenterId;

    @NotNull(message = "Service Type is mandatory")
    private Long serviceTypeId;

    @NotNull(message = "Cost is mandatory")
    @Min(value = 0, message = "Cost must be greater than or equal to 0")
    private Double cost;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Long serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
