package com.thedrone.app.repositories;

import com.thedrone.app.models.Delivery;
import com.thedrone.app.models.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

}
