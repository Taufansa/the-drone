package com.thedrone.app.components;

import com.thedrone.app.services.DroneService;
import com.thedrone.app.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class InitMethod implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DroneService droneService;

    @Autowired
    private MedicationService medicationService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        droneService.initRecordDrone();
        medicationService.initRecordMedication();
    }
}
