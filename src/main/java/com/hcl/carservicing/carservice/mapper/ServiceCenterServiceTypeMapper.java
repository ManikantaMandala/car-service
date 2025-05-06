package com.hcl.carservicing.carservice.mapper;

import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;

public class ServiceCenterServiceTypeMapper {

    public static ServiceCenterServiceTypeDTO convertToDTO(ServiceCenterServiceType serviceType) {
        ServiceCenterServiceTypeDTO serviceCenterServiceTypeDTO = new ServiceCenterServiceTypeDTO();

        serviceCenterServiceTypeDTO.setId(serviceType.getId());
        serviceCenterServiceTypeDTO.setServiceCenterId(serviceType.getServiceCenter().getId());
        serviceCenterServiceTypeDTO.setServiceTypeId(serviceType.getServiceType().getId());
        serviceCenterServiceTypeDTO.setCost(serviceType.getCost());

        return serviceCenterServiceTypeDTO;
    }

    public static ServiceCenterServiceType convertToEntity(ServiceCenterServiceTypeDTO serviceCenterServiceTypeDTO) {
        ServiceCenterServiceType serviceCenterServiceType = new ServiceCenterServiceType();

        serviceCenterServiceType.setId(serviceCenterServiceTypeDTO.getId());
        serviceCenterServiceType.setCost(serviceCenterServiceTypeDTO.getCost());

        return serviceCenterServiceType;
    }

}
