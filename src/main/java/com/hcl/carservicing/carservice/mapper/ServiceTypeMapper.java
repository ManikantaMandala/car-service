package com.hcl.carservicing.carservice.mapper;

import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.model.ServiceType;

public class ServiceTypeMapper {

    public static ServiceType convertToEntity(ServiceTypeDTO serviceTypeDTO) {
        ServiceType serviceType = new ServiceType();

        serviceType.setId(serviceTypeDTO.getId());
        serviceType.setServiceName(serviceTypeDTO.getServiceName());
        serviceType.setDescription(serviceTypeDTO.getDescription());

        return serviceType;
    }

    public static ServiceTypeDTO convertToDTO(ServiceType serviceType) {
        ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

        serviceTypeDTO.setId(serviceType.getId());
        serviceTypeDTO.setServiceName(serviceType.getServiceName());
        serviceTypeDTO.setDescription(serviceType.getDescription());

        return serviceTypeDTO;
    }

}
