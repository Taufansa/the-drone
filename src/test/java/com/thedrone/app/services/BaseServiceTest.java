package com.thedrone.app.services;

import com.thedrone.app.repositories.DeliveryItemRepository;
import com.thedrone.app.repositories.DeliveryRepository;
import com.thedrone.app.repositories.DroneRepository;
import com.thedrone.app.repositories.MedicationRepository;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseServiceTest {

    @Mock
    protected DroneRepository droneRepository;

    @Mock
    protected MedicationRepository medicationRepository;

    @Mock
    protected DeliveryRepository deliveryRepository;
    @Mock
    protected DeliveryItemRepository deliveryItemRepository;

}
