package com.hcl.carservicing.carservice.view;

import com.hcl.carservicing.carservice.client.ServiceRequestRestClient;
import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("/servicerequests")
public class ListView extends VerticalLayout{
    private final Grid<ServiceRequestDTO> serviceRequestGrid;
    private final ServiceRequestRestClient serviceRequestRestClient;
    private static final Logger logger = LoggerFactory.getLogger(ListView.class);

    public ListView(ServiceRequestRestClient serviceRequestRestClient) {
        this.serviceRequestRestClient = serviceRequestRestClient;

        serviceRequestGrid = new Grid<>(ServiceRequestDTO.class);

        serviceRequestGrid.addColumn(ServiceRequestDTO::getStartDate).setHeader("start date");
        serviceRequestGrid.addColumn(ServiceRequestDTO::getEndDate).setHeader("end date");
        serviceRequestGrid.addColumn(ServiceRequestDTO::getStatus).setHeader("request status");
        serviceRequestGrid.addColumn(ServiceRequestDTO::getUsername).setHeader("user");
        serviceRequestGrid.addColumn(ServiceRequestDTO::getDeliveryBoyId).setHeader("deliver boy");
        serviceRequestGrid.addColumn(ServiceRequestDTO::getServiceId).setHeader("service");
        serviceRequestGrid.addColumn(ServiceRequestDTO::getServiceCenterId).setHeader("service center");
        serviceRequestGrid.addColumn(ServiceRequestDTO::getVehicleName).setHeader("vehicle");

        String username = getUsername();

        setGrid(getServiceRequests(username));
    }

    private void setGrid(List<ServiceRequestDTO> serviceRequestList) {
        serviceRequestGrid.setItems(serviceRequestList);
    }

    private List<ServiceRequestDTO> getServiceRequests(String username) {
        // get username if it is null
        if(username == null) {
            username = getUsername();
        }

        return serviceRequestRestClient.fetchServiceRequests(username);
    }

    private String getUsername() {
        // get username from application
        return "string";
    }

}
