package com.thedrone.app.services;

import com.thedrone.app.repositories.DeliveryItemRepository;
import com.thedrone.app.repositories.DeliveryRepository;
import com.thedrone.app.repositories.DroneRepository;
import com.thedrone.app.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    @Autowired
    protected DroneRepository droneRepository;

    @Autowired
    protected MedicationRepository medicationRepository;

    @Autowired
    protected DeliveryRepository deliveryRepository;

    @Autowired
    protected DeliveryItemRepository deliveryItemRepository;
}
