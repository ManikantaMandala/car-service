package com.hcl.carservicing.carservice.mapper;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;
import com.hcl.carservicing.carservice.model.ServiceCenter;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceCenterMapper {

    public static ServiceCenter convertToEntity(ServiceCenterDTO serviceCenterDTO) {
        ServiceCenter serviceCenter = new ServiceCenter();

        serviceCenter.setId(serviceCenterDTO.getId());
        serviceCenter.setName(serviceCenterDTO.getName());
        serviceCenter.setAddress(serviceCenterDTO.getAddress());
        serviceCenter.setRating(serviceCenterDTO.getRating());
        serviceCenter.setAvailable(serviceCenterDTO.getAvailable());

        return serviceCenter;
    }

    public static ServiceCenterDTO convertToDTO(ServiceCenter serviceCenter) {
        ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();

        serviceCenterDTO.setId(serviceCenter.getId());
        serviceCenterDTO.setName(serviceCenter.getName());
        serviceCenterDTO.setAddress(serviceCenter.getAddress());
        serviceCenterDTO.setRating(serviceCenter.getRating());
        serviceCenterDTO.setAvailable(serviceCenter.getAvailable());

        if (serviceCenter.getServiceCenterServiceTypes() != null &&
                !serviceCenter.getServiceCenterServiceTypes().isEmpty()) {
            List<ServiceCenterServiceTypeDTO> serviceTypeDTOs = serviceCenter.getServiceCenterServiceTypes()
                    .stream()
                    .map(ServiceCenterServiceTypeMapper::convertToDTO)
                    .collect(Collectors.toList());
            serviceCenterDTO.setServiceCenterServiceTypes(serviceTypeDTOs);
        }

        return serviceCenterDTO;
    }
}
