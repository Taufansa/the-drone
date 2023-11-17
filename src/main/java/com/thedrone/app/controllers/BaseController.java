package com.thedrone.app.controllers;

import com.thedrone.app.enums.Constant;
import com.thedrone.app.services.DeliveryService;
import com.thedrone.app.services.DroneService;
import com.thedrone.app.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    protected DroneService droneService;

    @Autowired
    protected MedicationService medicationService;

    @Autowired
    protected DeliveryService deliveryService;

}
